#!/usr/bin/env python3

import subprocess
import re
import logging

# Set up logging for security monitoring
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

class SecureCommandRunner:
    """Secure command runner with strict input validation"""
    
    def __init__(self):
        # Define strict patterns for different command types
        self.command_patterns = {
            'simple': r'^[a-zA-Z0-9_-]+$',
            'file_path': r'^[a-zA-Z0-9/_.-]+$',
            'numeric': r'^\d+$',
            'alphanumeric_space': r'^[a-zA-Z0-9\s_-]+$'
        }
        
        # Blacklist of dangerous characters and patterns
        self.dangerous_patterns = [
            r'[;&|`$()]',      # Shell metacharacters
            r'\.\./',          # Directory traversal
            r'^\s*$',          # Empty or whitespace only
            r'[<>]',           # Redirection operators
            r'\*',             # Wildcards
            r'\\',             # Backslashes
        ]
    
    def validate_input(self, user_input, pattern_type='simple'):
        """Validate input against allowed patterns and blacklists"""
        if not user_input or not isinstance(user_input, str):
            logger.warning("Empty or non-string input rejected")
            return False
        
        # Check against dangerous patterns
        for pattern in self.dangerous_patterns:
            if re.search(pattern, user_input):
                logger.warning(f"Dangerous pattern detected: {pattern}")
                return False
        
        # Check against allowed pattern
        if pattern_type in self.command_patterns:
            if not re.match(self.command_patterns[pattern_type], user_input):
                logger.warning(f"Input doesn't match allowed pattern: {pattern_type}")
                return False
        
        # Additional length check
        if len(user_input) > 100:
            logger.warning("Input too long")
            return False
        
        return True
    
    def run_safe_command(self, command_args, input_types=None):
        """Run command with validated arguments"""
        if not isinstance(command_args, list) or len(command_args) == 0:
            logger.error("Invalid command arguments")
            return None
        
        # Validate each argument if types specified
        if input_types:
            if len(command_args[1:]) != len(input_types):
                logger.error("Argument count mismatch")
                return None
            
            for arg, arg_type in zip(command_args[1:], input_types):
                if not self.validate_input(arg, arg_type):
                    logger.error(f"Argument validation failed: {arg}")
                    return None
        
        # Log the command execution
        logger.info(f"Executing safe command: {' '.join(command_args)}")
        
        try:
            # Execute without shell=True for security
            result = subprocess.run(
                command_args,
                capture_output=True,
                text=True,
                timeout=10,
                check=False
            )
            
            logger.info(f"Command completed with return code: {result.returncode}")
            return result
            
        except subprocess.TimeoutExpired:
            logger.error("Command timeout")
            return None
        except Exception as e:
            logger.error(f"Command execution failed: {e}")
            return None

def run_command_list(cmd_args):
    """Secure version of command list runner"""
    runner = SecureCommandRunner()
    
    if not cmd_args or len(cmd_args) == 0:
        print("Error: No command provided")
        return None
    
    # Only allow specific safe commands
    allowed_commands = ['echo', 'date', 'whoami', 'pwd']
    
    if cmd_args[0] not in allowed_commands:
        print(f"Error: Command '{cmd_args[0]}' not allowed")
        logger.warning(f"Blocked disallowed command: {cmd_args[0]}")
        return None
    
    # Validate all arguments
    for arg in cmd_args[1:]:
        if not runner.validate_input(arg, 'alphanumeric_space'):
            print(f"Error: Invalid argument '{arg}'")
            return None
    
    result = runner.run_safe_command(cmd_args)
    if result:
        print("STDOUT:", result.stdout)
        if result.stderr:
            print("STDERR:", result.stderr)
    
    return result

def safe_program_runner(program, *args):
    """Secure version that prevents shell injection"""
    runner = SecureCommandRunner()
    
    # Validate program name
    if not runner.validate_input(program, 'simple'):
        print("Error: Invalid program name")
        return None
    
    # Only allow specific programs
    allowed_programs = ['echo', 'date', 'whoami']
    if program not in allowed_programs:
        print(f"Error: Program '{program}' not allowed")
        return None
    
    # Build command list safely
    command_list = [program]
    for arg in args:
        if not runner.validate_input(str(arg), 'alphanumeric_space'):
            print(f"Error: Invalid argument '{arg}'")
            return None
        command_list.append(str(arg))
    
    result = runner.run_safe_command(command_list)
    if result:
        print("STDOUT:", result.stdout)
        if result.stderr:
            print("STDERR:", result.stderr)
    
    return result

if __name__ == "__main__":
    print("Secure Command Runner - Only safe commands allowed")
    print("Allowed commands: echo, date, whoami, pwd")
    
    user_command = input("Enter command and args (space-separated): ").split()
    if user_command:
        run_command_list(user_command)
    
    program = input("Enter program name: ")
    arguments = input("Enter arguments: ")
    if program:
        safe_program_runner(program, arguments)