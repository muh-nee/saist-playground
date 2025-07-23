#!/usr/bin/env python3

def execute_python_code(code):
    try:
        exec(code)
        print("Code executed successfully")
    except Exception as e:
        print(f"Execution error: {e}")

def dynamic_function_creation(func_name, func_body):
    function_template = f"""
def {func_name}():
    {func_body}
"""
    exec(function_template, globals())
    print(f"Function {func_name} created")

def template_processor(template, variables):
    processed = template
    for var_name, var_value in variables.items():
        exec(f"{var_name} = {repr(var_value)}")
        processed = processed.replace(f"{{{var_name}}}", str(var_value))
    
    if "{{exec:" in processed:
        start = processed.find("{{exec:")
        end = processed.find("}}", start)
        if end != -1:
            code = processed[start+7:end]
            exec(code)
    
    return processed

if __name__ == "__main__":
    user_code = input("Enter Python code to execute: ")
    execute_python_code(user_code)
    
    func_name = input("Enter function name: ")
    func_body = input("Enter function body: ")
    dynamic_function_creation(func_name, func_body)
    
    template = input("Enter template: ")
    variables = {"name": "user", "value": "test"}
    result = template_processor(template, variables)
    print(f"Template result: {result}")