#!/usr/bin/env python3

import subprocess

def run_command_list(cmd_args):
    result = subprocess.run(cmd_args, shell=True, capture_output=True, text=True)
    print("STDOUT:", result.stdout)
    print("STDERR:", result.stderr)
    return result

def safe_looking_but_vulnerable(program, *args):
    command_list = [program] + list(args)
    return subprocess.run(command_list, shell=True, capture_output=True, text=True)

if __name__ == "__main__":
    user_command = input("Enter command and args (space-separated): ").split()
    run_command_list(user_command)
    
    program = input("Enter program name: ")
    arguments = input("Enter arguments: ")
    safe_looking_but_vulnerable(program, arguments)