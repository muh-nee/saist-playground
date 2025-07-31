from lxml import etree
import re
import html
import sys

class SecureXPathProcessor:
    
    def __init__(self):
        self.allowed_fields = {
            'username', 'role', 'department', 'status', 'id'
        }
        
        self.allowed_values = {
            'role': ['admin', 'user', 'guest'],
            'department': ['hr', 'engineering', 'marketing', 'finance'],
            'status': ['active', 'inactive', 'suspended']
        }
    
    def sanitize_input(self, user_input):
        if not isinstance(user_input, str):
            raise ValueError("Input must be a string")
        
        if len(user_input) > 50:
            raise ValueError("Input too long")
        
        dangerous_chars = ["'", '"', '(', ')', '[', ']', '|', '&', '<', '>', '=', '!', ';', '@', '$', '%']
        sanitized = user_input
        
        for char in dangerous_chars:
            sanitized = sanitized.replace(char, '')
        
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        sanitized = html.escape(sanitized)
        
        if not re.match(r'^[a-zA-Z0-9\s._-]+$', sanitized):
            raise ValueError("Input contains invalid characters")
        
        return sanitized
    
    def validate_field_name(self, field_name):
        return field_name in self.allowed_fields
    
    def validate_field_value(self, field_name, value):
        if field_name in self.allowed_values:
            return value in self.allowed_values[field_name]
        return True

def secure_user_lookup(username):
    xml_data = """
    <users>
        <user id="1">
            <username>admin</username>
            <password>secret123</password>
            <role>administrator</role>
        </user>
        <user id="2">
            <username>guest</username>
            <password>guest123</password>
            <role>user</role>
        </user>
        <user id="3">
            <username>john</username>
            <password>mypassword</password>
            <role>user</role>
        </user>
    </users>
    """
    
    processor = SecureXPathProcessor()
    
    try:
        sanitized_username = processor.sanitize_input(username)
        
        if not processor.validate_field_value('username', sanitized_username):
            return None
        
        root = etree.fromstring(xml_data)
        
        for user_elem in root.xpath("//user"):
            username_elem = user_elem.find("username")
            if username_elem is not None and username_elem.text == sanitized_username:
                return {
                    'username': username_elem.text,
                    'role': user_elem.find('role').text
                }
        
        return None
        
    except (ValueError, etree.XPathEvalError) as e:
        print(f"Error: {e}")
        return None

if __name__ == "__main__":
    if len(sys.argv) > 1:
        user_input = sys.argv[1]
        result = secure_user_lookup(user_input)
        if result:
            print(f"User found: {result}")
        else:
            print("User not found or invalid input")
    else:
        print("Usage: python secure1.py <username>")