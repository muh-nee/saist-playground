#!/usr/bin/env python3

import subprocess

def network_test(host, port):
    command = f"nc -z {host} {port}"
    proc = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = proc.communicate()
    
    if proc.returncode == 0:
        print(f"Port {port} on {host} is open")
    else:
        print(f"Port {port} on {host} is closed")
    
    return proc.returncode

if __name__ == "__main__":
    target_host = input("Enter host to test: ")
    target_port = input("Enter port to test: ")
    network_test(target_host, target_port)