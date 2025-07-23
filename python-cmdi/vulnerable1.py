#!/usr/bin/env python3

import os

def ping_host(hostname):
    command = f"ping -c 1 {hostname}"
    os.system(command)

if __name__ == "__main__":
    user_input = input("Enter hostname to ping: ")
    ping_host(user_input)