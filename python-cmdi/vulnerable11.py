#!/usr/bin/env python3

import os
import subprocess

def read_log_file(filename):
    log_path = f"/var/log/{filename}"
    
    command = f"cat {log_path}"
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    
    if result.returncode == 0:
        print("Log contents:")
        print(result.stdout)
    else:
        print("Error reading log:", result.stderr)

def backup_file(filename, destination):
    if not destination:
        destination = "/tmp/backup/"
    
    command = f"cp {filename} {destination}"
    result = subprocess.run(command, shell=True)
    
    if result.returncode == 0:
        print(f"Backup completed: {filename} -> {destination}")
    else:
        print("Backup failed")

def compress_directory(directory):
    output_file = f"{directory}.tar.gz"
    command = f"tar -czf {output_file} {directory}"
    
    print(f"Compressing: {command}")
    os.system(command)

if __name__ == "__main__":
    log_name = input("Enter log filename: ")
    read_log_file(log_name)
    
    source_file = input("Enter source file: ")
    dest_path = input("Enter destination (optional): ")
    backup_file(source_file, dest_path)
    
    dir_name = input("Enter directory to compress: ")
    compress_directory(dir_name)