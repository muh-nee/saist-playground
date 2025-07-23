#!/usr/bin/env python3

import ast
import subprocess
import tempfile
import os
import sys
import time
import hashlib
import uuid
from functools import wraps

class ComprehensiveSecurityFramework:
    """
    A comprehensive security framework demonstrating multiple layers of protection
    against code injection, command injection, and other security vulnerabilities.
    """
    
    def __init__(self):
        self.session_id = str(uuid.uuid4())
        self.security_log = []
        self.rate_limiter = {}
        self.max_requests_per_minute = 10
        
    def log_security_event(self, event_type, details, severity="INFO"):
        """Log security events for monitoring"""
        timestamp = time.time()
        event = {
            'timestamp': timestamp,
            'session_id': self.session_id,
            'event_type': event_type,
            'details': details,
            'severity': severity
        }
        self.security_log.append(event)
        print(f"[{severity}] {event_type}: {details}")
    
    def rate_limit(self, key, max_requests=None):
        """Simple rate limiting decorator"""
        if max_requests is None:
            max_requests = self.max_requests_per_minute
        
        def decorator(func):
            @wraps(func)
            def wrapper(*args, **kwargs):
                current_time = time.time()
                if key not in self.rate_limiter:
                    self.rate_limiter[key] = []
                
                # Clean old requests (older than 1 minute)
                self.rate_limiter[key] = [
                    req_time for req_time in self.rate_limiter[key]
                    if current_time - req_time < 60
                ]
                
                if len(self.rate_limiter[key]) >= max_requests:
                    self.log_security_event(
                        "RATE_LIMIT_EXCEEDED", 
                        f"Key: {key}", 
                        "WARNING"
                    )
                    return None
                
                self.rate_limiter[key].append(current_time)
                return func(*args, **kwargs)
            return wrapper
        return decorator
    
    def sanitize_filename(self, filename):
        """Sanitize filename to prevent directory traversal"""
        if not filename or not isinstance(filename, str):
            return None
        
        # Remove directory traversal attempts
        filename = os.path.basename(filename)
        
        # Allow only alphanumeric, dots, hyphens, and underscores
        import re
        if not re.match(r'^[a-zA-Z0-9._-]+$', filename):
            return None
        
        # Prevent hidden files and files with multiple extensions
        if filename.startswith('.') or filename.count('.') > 1:
            return None
        
        return filename
    
    def secure_file_operations(self, content, file_extension=".txt"):
        """Demonstrate secure file operations using temporary files"""
        try:
            # Create secure temporary file
            with tempfile.NamedTemporaryFile(
                mode='w',
                suffix=file_extension,
                delete=False,
                prefix='secure_'
            ) as temp_file:
                temp_file.write(content)
                temp_filename = temp_file.name
            
            self.log_security_event(
                "SECURE_FILE_CREATED", 
                f"Temp file: {temp_filename}"
            )
            
            # Read it back securely
            with open(temp_filename, 'r') as f:
                read_content = f.read()
            
            # Clean up
            os.unlink(temp_filename)
            
            return read_content
            
        except Exception as e:
            self.log_security_event(
                "FILE_OPERATION_ERROR", 
                str(e), 
                "ERROR"
            )
            return None
    
    def secure_subprocess_execution(self, command_list, allowed_commands=None):
        """Execute subprocess with comprehensive security measures"""
        if allowed_commands is None:
            allowed_commands = ['echo', 'date', 'whoami', 'pwd', 'ls']
        
        if not isinstance(command_list, list) or len(command_list) == 0:
            self.log_security_event(
                "INVALID_COMMAND_FORMAT", 
                "Command must be a non-empty list", 
                "WARNING"
            )
            return None
        
        # Validate command is in allowlist
        if command_list[0] not in allowed_commands:
            self.log_security_event(
                "BLOCKED_COMMAND", 
                f"Command '{command_list[0]}' not in allowlist", 
                "WARNING"
            )
            return None
        
        # Validate all arguments
        for arg in command_list[1:]:
            if not isinstance(arg, str):
                self.log_security_event(
                    "INVALID_ARGUMENT_TYPE", 
                    f"Non-string argument: {type(arg)}", 
                    "WARNING"
                )
                return None
            
            # Check for dangerous characters
            dangerous_chars = [';', '|', '&', '$', '`', '(', ')', '<', '>', '*']
            if any(char in arg for char in dangerous_chars):
                self.log_security_event(
                    "DANGEROUS_CHARACTER_DETECTED", 
                    f"Argument contains dangerous characters: {arg}", 
                    "WARNING"
                )
                return None
        
        try:
            # Execute with strict security settings
            result = subprocess.run(
                command_list,
                capture_output=True,
                text=True,
                timeout=5,  # Short timeout
                check=False,
                env={'PATH': '/usr/bin:/bin'},  # Restricted PATH
                cwd='/tmp'  # Safe working directory
            )
            
            self.log_security_event(
                "COMMAND_EXECUTED", 
                f"Command: {' '.join(command_list)}, Return code: {result.returncode}"
            )
            
            return result
            
        except subprocess.TimeoutExpired:
            self.log_security_event(
                "COMMAND_TIMEOUT", 
                f"Command timed out: {' '.join(command_list)}", 
                "WARNING"
            )
            return None
        except Exception as e:
            self.log_security_event(
                "COMMAND_EXECUTION_ERROR", 
                str(e), 
                "ERROR"
            )
            return None
    
    def secure_eval_alternative(self, expression):
        """Secure alternative to eval() using AST"""
        if not isinstance(expression, str) or len(expression) > 100:
            return None
        
        # Only allow mathematical expressions
        import re
        if not re.match(r'^[0-9+\-*/().\s]+$', expression):
            self.log_security_event(
                "INVALID_EXPRESSION", 
                f"Expression contains invalid characters: {expression}", 
                "WARNING"
            )
            return None
        
        try:
            # Parse and evaluate safely using AST
            node = ast.parse(expression, mode='eval')
            
            # Define allowed operations
            allowed_ops = {
                ast.Add: lambda x, y: x + y,
                ast.Sub: lambda x, y: x - y,
                ast.Mult: lambda x, y: x * y,
                ast.Div: lambda x, y: x / y if y != 0 else float('inf'),
            }
            
            def eval_node(node):
                if isinstance(node, ast.Num):
                    return node.n
                elif isinstance(node, ast.Constant):
                    return node.value
                elif isinstance(node, ast.BinOp):
                    left = eval_node(node.left)
                    right = eval_node(node.right)
                    op = allowed_ops.get(type(node.op))
                    if op:
                        return op(left, right)
                    else:
                        raise ValueError("Unsupported operation")
                else:
                    raise ValueError("Unsupported node type")
            
            result = eval_node(node.body)
            self.log_security_event(
                "SAFE_EXPRESSION_EVALUATED", 
                f"Expression: {expression}, Result: {result}"
            )
            return result
            
        except Exception as e:
            self.log_security_event(
                "EXPRESSION_EVALUATION_ERROR", 
                str(e), 
                "WARNING"
            )
            return None
    
    @rate_limit('code_execution', 3)  # Very restrictive for code execution
    def secure_code_sandbox(self, code_snippet):
        """
        Demonstrate a secure approach to code execution using restricted environment
        NOTE: This is still risky and should only be used in isolated environments
        """
        if not isinstance(code_snippet, str) or len(code_snippet) > 200:
            return None
        
        # Blacklist dangerous functions and modules
        dangerous_keywords = [
            'import', 'exec', 'eval', 'open', 'file', '__import__',
            'compile', 'globals', 'locals', 'vars', 'dir',
            'getattr', 'setattr', 'delattr', 'hasattr',
            'subprocess', 'os', 'sys'
        ]
        
        for keyword in dangerous_keywords:
            if keyword in code_snippet.lower():
                self.log_security_event(
                    "DANGEROUS_CODE_BLOCKED", 
                    f"Code contains dangerous keyword: {keyword}", 
                    "WARNING"
                )
                return None
        
        # Create restricted environment
        safe_builtins = {
            'abs': abs, 'max': max, 'min': min, 'len': len,
            'str': str, 'int': int, 'float': float, 'bool': bool,
            'print': print, 'range': range, 'sum': sum
        }
        
        try:
            # Execute in restricted namespace
            namespace = {'__builtins__': safe_builtins}
            exec(code_snippet, namespace)
            
            self.log_security_event(
                "SANDBOXED_CODE_EXECUTED", 
                f"Code length: {len(code_snippet)} chars"
            )
            return "Code executed successfully in sandbox"
            
        except Exception as e:
            self.log_security_event(
                "SANDBOXED_CODE_ERROR", 
                str(e), 
                "WARNING"
            )
            return f"Execution error: {e}"
    
    def security_report(self):
        """Generate a security report of all logged events"""
        print("\n" + "="*50)
        print("SECURITY REPORT")
        print("="*50)
        print(f"Session ID: {self.session_id}")
        print(f"Total Events: {len(self.security_log)}")
        
        severity_counts = {}
        for event in self.security_log:
            severity = event['severity']
            severity_counts[severity] = severity_counts.get(severity, 0) + 1
        
        print("Event Summary:")
        for severity, count in severity_counts.items():
            print(f"  {severity}: {count}")
        
        print("\nRecent Events:")
        for event in self.security_log[-10:]:  # Last 10 events
            print(f"  [{event['severity']}] {event['event_type']}: {event['details']}")

def demonstrate_security_features():
    """Demonstrate all security features"""
    framework = ComprehensiveSecurityFramework()
    
    print("Comprehensive Security Framework Demo")
    print("=" * 50)
    
    # 1. Secure file operations
    print("\n1. Secure File Operations:")
    content = framework.secure_file_operations("Hello, secure world!")
    print(f"File content: {content}")
    
    # 2. Secure subprocess execution
    print("\n2. Secure Subprocess Execution:")
    result = framework.secure_subprocess_execution(['echo', 'Hello from subprocess'])
    if result:
        print(f"Output: {result.stdout.strip()}")
    
    # 3. Secure expression evaluation
    print("\n3. Secure Expression Evaluation:")
    result = framework.secure_eval_alternative("2 + 3 * 4")
    print(f"Result: {result}")
    
    # 4. Demonstrate rate limiting
    print("\n4. Rate Limiting Demo:")
    for i in range(5):
        result = framework.secure_code_sandbox(f"print('Test {i}')")
        if result is None:
            print("Rate limit reached!")
            break
    
    # 5. Security report
    framework.security_report()

if __name__ == "__main__":
    demonstrate_security_features()
    
    print("\n" + "="*50)
    print("Interactive Security Demo")
    print("="*50)
    
    framework = ComprehensiveSecurityFramework()
    
    while True:
        print("\nAvailable options:")
        print("1. Execute secure command")
        print("2. Evaluate math expression")
        print("3. Create secure file")
        print("4. View security report")
        print("5. Exit")
        
        choice = input("\nEnter choice (1-5): ").strip()
        
        if choice == '1':
            cmd = input("Enter command (echo, date, whoami, pwd): ")
            args = input("Enter arguments (optional): ").split()
            command_list = [cmd] + args if args else [cmd]
            result = framework.secure_subprocess_execution(command_list)
            if result:
                print(f"Output: {result.stdout}")
        
        elif choice == '2':
            expr = input("Enter math expression: ")
            result = framework.secure_eval_alternative(expr)
            print(f"Result: {result}")
        
        elif choice == '3':
            content = input("Enter file content: ")
            result = framework.secure_file_operations(content)
            print(f"File processing result: {result}")
        
        elif choice == '4':
            framework.security_report()
        
        elif choice == '5':
            print("Exiting...")
            break
        
        else:
            print("Invalid choice!")