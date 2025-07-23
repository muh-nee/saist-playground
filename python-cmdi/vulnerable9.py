#!/usr/bin/env python3

import os

def calculate_expression(expression):
    try:
        result = eval(expression)
        print(f"Result: {result}")
        return result
    except Exception as e:
        print(f"Error: {e}")
        return None

def config_parser(config_string):
    config = {}
    for line in config_string.split('\n'):
        if '=' in line:
            key, value = line.split('=', 1)
            config[key.strip()] = eval(value.strip())
    return config

if __name__ == "__main__":
    user_expr = input("Enter mathematical expression: ")
    calculate_expression(user_expr)
    
    config_input = input("Enter config (key=value format): ")
    config_parser(config_input)