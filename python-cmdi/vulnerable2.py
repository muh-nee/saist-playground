#!/usr/bin/env python3

import subprocess

def count_lines(filename):
    command = f"wc -l {filename}"
    result = subprocess.call(command, shell=True)
    return result

if __name__ == "__main__":
    user_file = input("Enter filename to count lines: ")
    count_lines(user_file)