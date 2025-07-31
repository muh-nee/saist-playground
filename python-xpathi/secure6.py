import tornado.ioloop
import tornado.web
import xml.etree.ElementTree as ET
import re
import html

class SecureTornadoHandler(tornado.web.RequestHandler):
    
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.allowed_roles = {
            'admin', 'user', 'guest', 'manager'
        }
        
        self.role_patterns = {
            'admin': r'^admin$',
            'user': r'^user$',
            'guest': r'^guest$',
            'manager': r'^manager$'
        }
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 20:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w-]', '', sanitized)
        
        return sanitized.lower()
    
    def validate_role(self, role):
        sanitized_role = self.sanitize_input(role)
        
        if sanitized_role not in self.allowed_roles:
            return False
        
        pattern = self.role_patterns.get(sanitized_role)
        if pattern and not re.match(pattern, sanitized_role):
            return False
        
        return True
    
    def get(self, user_role):
        xml_data = """
        <system>
            <users>
                <user id="1" role="admin" active="true">
                    <username>administrator</username>
                    <email>admin@company.com</email>
                    <permissions>all</permissions>
                    <salary>150000</salary>
                </user>
                <user id="2" role="user" active="true">
                    <username>john_doe</username>
                    <email>john@company.com</email>
                    <permissions>read</permissions>
                    <salary>75000</salary>
                </user>
                <user id="3" role="guest" active="false">
                    <username>temp_guest</username>
                    <email>guest@company.com</email>
                    <permissions>none</permissions>
                    <salary>0</salary>
                </user>
            </users>
        </system>
        """
        
        try:
            if not self.validate_role(user_role):
                self.set_status(400)
                self.write({"error": "Invalid or unauthorized role"})
                return
            
            sanitized_role = self.sanitize_input(user_role)
            
            root = ET.fromstring(xml_data)
            users = []
            
            for user in root.findall(".//user"):
                if user.get('role') == sanitized_role:
                    users.append({
                        'username': user.find('username').text,
                        'role': user.get('role'),
                        'email': user.find('email').text,
                        'permissions': user.find('permissions').text
                    })
            
            if not users:
                self.set_status(404)
                self.write({"error": "No users found for role"})
                return
            
            self.write({"users": users})
            
        except (ValueError, ET.ParseError) as e:
            self.set_status(500)
            self.write({"error": str(e)})

def make_app():
    return tornado.web.Application([
        (r"/users/([^/]+)", SecureTornadoHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8888, address='127.0.0.1')
    print("Server running on http://127.0.0.1:8888")
    tornado.ioloop.IOLoop.current().start()