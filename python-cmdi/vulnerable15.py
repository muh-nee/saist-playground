#!/usr/bin/env python3

import os
import subprocess

def backup_with_env_path():
    backup_path = os.environ.get('BACKUP_PATH', '/tmp/backup')
    
    command = f"tar -czf {backup_path}/backup.tar.gz /home/user/documents"
    print(f"Running: {command}")
    
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    return result

def process_with_env_config():
    log_level = os.environ.get('LOG_LEVEL', 'info')
    output_dir = os.environ.get('OUTPUT_DIR', '/var/log')
    service_name = os.environ.get('SERVICE_NAME', 'myapp')
    
    command = f"logger -p {log_level} -t {service_name} 'Starting service' > {output_dir}/service.log"
    
    print(f"Executing: {command}")
    os.system(command)

def custom_path_execution():
    custom_path = os.environ.get('CUSTOM_EXEC_PATH', '/usr/bin')
    program_name = os.environ.get('PROGRAM_NAME', 'ls')
    program_args = os.environ.get('PROGRAM_ARGS', '-la')
    
    full_command = f"{custom_path}/{program_name} {program_args}"
    
    print(f"Executing from custom path: {full_command}")
    result = subprocess.run(full_command, shell=True, capture_output=True, text=True)
    
    print("STDOUT:", result.stdout)
    print("STDERR:", result.stderr)

def database_backup():
    db_host = os.environ.get('DB_HOST', 'localhost')
    db_user = os.environ.get('DB_USER', 'root')
    db_pass = os.environ.get('DB_PASS', 'password')
    db_name = os.environ.get('DB_NAME', 'mydb')
    backup_cmd = os.environ.get('BACKUP_CMD', 'mysqldump')
    
    command = f"{backup_cmd} -h{db_host} -u{db_user} -p{db_pass} {db_name} > backup.sql"
    
    print(f"Database backup command: {command}")
    os.system(command)

if __name__ == "__main__":
    print("=== Environment Variable Command Injection Examples ===")
    
    os.environ['BACKUP_PATH'] = input("Set BACKUP_PATH: ")
    os.environ['LOG_LEVEL'] = input("Set LOG_LEVEL: ")
    os.environ['OUTPUT_DIR'] = input("Set OUTPUT_DIR: ")
    os.environ['SERVICE_NAME'] = input("Set SERVICE_NAME: ")
    
    print("\n1. Backup with environment path:")
    backup_with_env_path()
    
    print("\n2. Process with environment config:")
    process_with_env_config()
    
    print("\n3. Custom path execution:")
    custom_path_execution()
    
    print("\n4. Database backup:")
    database_backup()