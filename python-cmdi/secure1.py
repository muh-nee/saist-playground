#!/usr/bin/env python3

import subprocess
import re

def ping_host(hostname):
    # Validate hostname format (alphanumeric, dots, hyphens only)
    if not re.match(r'^[a-zA-Z0-9.-]+$', hostname):
        print("Error: Invalid hostname format")
        return False
    
    # Use subprocess with argument list (no shell=True)
    try:
        result = subprocess.run(['ping', '-c', '1', hostname], 
                              capture_output=True, text=True, timeout=10)
        print(result.stdout)
        return result.returncode == 0
    except subprocess.TimeoutExpired:
        print("Error: Ping timeout")
        return False
    except Exception as e:
        print(f"Error: {e}")
        return False

if __name__ == "__main__":
    user_input = input("Enter hostname to ping: ")
    ping_host(user_input)