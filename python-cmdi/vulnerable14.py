#!/usr/bin/env python3

import shlex
import subprocess
import os

def unsafe_shlex_parsing(command_string):
    try:
        args = shlex.split(command_string)
        print(f"Parsed args: {args}")
        
        result = subprocess.run(args, shell=True, capture_output=True, text=True)
        return result.stdout
    except Exception as e:
        print(f"Error: {e}")
        return None

def command_builder(base_cmd, user_args):
    quoted_args = []
    for arg in user_args:
        if ';' in arg or '&' in arg or '|' in arg:
            print("Warning: Special characters detected")
        quoted_args.append(shlex.quote(arg))
    
    full_command = f"{base_cmd} {' '.join(quoted_args)}"
    print(f"Executing: {full_command}")
    
    return os.system(full_command)

def pseudo_safe_execution(command_parts):
    if not isinstance(command_parts, list):
        command_parts = shlex.split(command_parts)
    
    program = command_parts[0]
    args = command_parts[1:] if len(command_parts) > 1 else []
    
    full_args = [program] + args
    result = subprocess.run(full_args, shell=True, capture_output=True, text=True)
    
    return result

if __name__ == "__main__":
    user_command = input("Enter command: ")
    output = unsafe_shlex_parsing(user_command)
    print("Output:", output)
    
    base = input("Enter base command: ")
    args_input = input("Enter arguments (space-separated): ")
    user_args = args_input.split()
    command_builder(base, user_args)
    
    cmd_input = input("Enter command with args: ")
    result = pseudo_safe_execution(cmd_input)
    print("Result:", result.stdout)