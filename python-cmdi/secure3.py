#!/usr/bin/env python3

import subprocess
import ipaddress
import socket

def trace_route(target):
    # Validate input is a valid IP address or hostname
    try:
        # Try to parse as IP address first
        ip_obj = ipaddress.ip_address(target)
        target_ip = str(ip_obj)
    except ValueError:
        # If not an IP, validate as hostname and resolve
        try:
            if not target.replace('-', '').replace('.', '').isalnum():
                raise ValueError("Invalid hostname format")
            target_ip = socket.gethostbyname(target)
        except (socket.gaierror, ValueError):
            print("Error: Invalid target (must be valid IP or hostname)")
            return None
    
    try:
        # Use traceroute with argument list for security
        result = subprocess.run(['traceroute', target_ip], 
                              capture_output=True, text=True, timeout=30)
        print(result.stdout)
        if result.stderr:
            print(f"Warnings: {result.stderr}")
        return result
    except subprocess.TimeoutExpired:
        print("Error: Traceroute timeout")
        return None
    except FileNotFoundError:
        print("Error: traceroute command not found")
        return None
    except Exception as e:
        print(f"Error: {e}")
        return None

if __name__ == "__main__":
    target_host = input("Enter target to trace route: ")
    trace_route(target_host)