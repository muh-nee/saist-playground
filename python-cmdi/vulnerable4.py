#!/usr/bin/env python3

import os

def check_disk_usage(directory):
    command = f"du -sh {directory}"
    with os.popen(command) as proc:
        output = proc.read()
    print(output)
    return output

if __name__ == "__main__":
    user_dir = input("Enter directory to check disk usage: ")
    check_disk_usage(user_dir)