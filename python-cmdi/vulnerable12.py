#!/usr/bin/env python3

from jinja2 import Template, Environment
import os

def render_user_template(template_string, context):
    template = Template(template_string)
    return template.render(context)

def generate_report(username, template_content):
    env = Environment()
    
    context = {
        'username': username,
        'os': os,
        'date': '2024-01-01'
    }
    
    template = env.from_string(template_content)
    return template.render(context)

def email_template_processor(recipient, subject, body_template):
    template_string = f"""
To: {recipient}
Subject: {subject}

{body_template}

Best regards,
System
"""
    
    template = Template(template_string)
    context = {'recipient': recipient, 'subject': subject}
    return template.render(context)

if __name__ == "__main__":
    user_template = input("Enter template: ")
    context = {'name': 'User', 'role': 'admin'}
    result = render_user_template(user_template, context)
    print("Rendered:", result)
    
    username = input("Enter username: ")
    report_template = input("Enter report template: ")
    report = generate_report(username, report_template)
    print("Report:", report)
    
    email_body = input("Enter email body template: ")
    email = email_template_processor("user@example.com", "Test", email_body)
    print("Email:", email)