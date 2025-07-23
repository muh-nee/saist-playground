#!/usr/bin/env python3

import subprocess
import socket
import re
import ipaddress

def network_test(host, port):
    # Validate port is numeric and in valid range
    try:
        port_num = int(port)
        if not (1 <= port_num <= 65535):
            print("Error: Port must be between 1 and 65535")
            return -1
    except ValueError:
        print("Error: Port must be a number")
        return -1
    
    # Validate host input
    try:
        # Try to parse as IP address
        ip_obj = ipaddress.ip_address(host)
        target_host = str(ip_obj)
    except ValueError:
        # If not IP, validate hostname format
        if not re.match(r'^[a-zA-Z0-9.-]+$', host):
            print("Error: Invalid host format")
            return -1
        target_host = host
    
    # Use Python socket instead of nc command for security
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
            sock.settimeout(5)  # 5 second timeout
            result = sock.connect_ex((target_host, port_num))
            
            if result == 0:
                print(f"Port {port_num} on {target_host} is open")
                return 0
            else:
                print(f"Port {port_num} on {target_host} is closed")
                return 1
                
    except socket.gaierror:
        print(f"Error: Could not resolve hostname {target_host}")
        return -1
    except Exception as e:
        print(f"Error: {e}")
        return -1

if __name__ == "__main__":
    target_host = input("Enter host to test: ")
    target_port = input("Enter port to test: ")
    network_test(target_host, target_port)