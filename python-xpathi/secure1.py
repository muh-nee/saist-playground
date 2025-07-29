
from lxml import etree
import re
import html

class SecureXPathProcessor:
    
    def __init__(self):
        self.allowed_fields = {
            'username': '@username',
            'role': '@role', 
            'department': '@department',
            'status': '@status',
            'id': '@id'
        }
        
        self.allowed_values = {
            'role': ['admin', 'user', 'guest'],
            'department': ['hr', 'engineering', 'marketing', 'finance'],
            'status': ['active', 'inactive', 'suspended'],
            'clearance': ['public', 'internal', 'confidential']
        }
    
    def sanitize_input(self, user_input):
        if not isinstance(user_input, str):
            raise ValueError("Input must be a string")
        
        dangerous_chars = ["'", '"', '(', ')', '[', ']', '|', '&', '<', '>', '=', '!']
        sanitized = user_input
        
        for char in dangerous_chars:
            sanitized = sanitized.replace(char, '')
        
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        sanitized = html.escape(sanitized)
        
        return sanitized
    
    def validate_field_name(self, field_name):
        return field_name in self.allowed_fields
    
    def validate_field_value(self, field_name, value):
        if field_name in self.allowed_values:
            return value in self.allowed_values[field_name]
        
        if len(value) > 50:
            return False
        
        if not re.match(r'^[a-zA-Z0-9\s._-]+$', value):
            return False
            
        return True

def secure_user_lookup(processor, field_name, field_value):
    xml_data = """
    <users>
        <user id="1" username="admin" role="admin" department="engineering" status="active">
            <name>Administrator</name>
            <email>admin@company.com</email>
            <clearance>confidential</clearance>
        </user>
        <user id="2" username="john_doe" role="user" department="marketing" status="active">
            <name>John Doe</name>
            <email>john@company.com</email>
            <clearance>internal</clearance>
        </user>
        <user id="3" username="guest_user" role="guest" department="hr" status="inactive">
            <name>Guest User</name>
            <email>guest@company.com</email>
            <clearance>public</clearance>
        </user>
    </users>
    <employees>
        <employee id="101" role="manager" department="engineering" status="active">
            <name>Alice Johnson</name>
            <position>Engineering Manager</position>
            <clearance>confidential</clearance>
        </employee>
        <employee id="102" role="developer" department="engineering" status="active">
            <name>Bob Smith</name>
            <position>Senior Developer</position>
            <clearance>internal</clearance>
        </employee>
        <employee id="103" role="analyst" department="finance" status="suspended">
            <name>Carol Davis</name>
            <position>Financial Analyst</position>
            <clearance>internal</clearance>
        </employee>
    </employees>
