#!/usr/bin/env python3

import subprocess

def trace_route(target):
    command = f"traceroute {target}"
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    print(result.stdout)
    return result

if __name__ == "__main__":
    target_host = input("Enter target to trace route: ")
    trace_route(target_host)