import cherrypy
import xml.etree.ElementTree as ET
import re
import html

class SecureCherryPyApp:
    
    def __init__(self):
        self.allowed_clearance_levels = {
            'standard', 'secret', 'top_secret'
        }
        
        self.clearance_validators = {
            'standard': r'^standard$',
            'secret': r'^secret$',
            'top_secret': r'^top_secret$'
        }
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 15:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w_]', '', sanitized)
        
        return sanitized.lower()
    
    def validate_clearance(self, clearance_level):
        sanitized_clearance = self.sanitize_input(clearance_level)
        
        if sanitized_clearance not in self.allowed_clearance_levels:
            return False
        
        pattern = self.clearance_validators.get(sanitized_clearance)
        if pattern and not re.match(pattern, sanitized_clearance):
            return False
        
        return True
    
    @cherrypy.expose
    @cherrypy.tools.json_out()
    def employees(self, clearance_level=None):
        xml_data = """
        <personnel>
            <employees>
                <employee id="1001" clearance="standard" status="active">
                    <name>John Smith</name>
                    <department>IT Support</department>
                    <salary>55000</salary>
                </employee>
                <employee id="1002" clearance="secret" status="active">
                    <name>Sarah Connor</name>
                    <department>Cybersecurity</department>
                    <salary>95000</salary>
                </employee>
                <employee id="1003" clearance="top_secret" status="suspended">
                    <name>James Bond</name>
                    <department>Special Operations</department>
                    <salary>150000</salary>
                </employee>
            </employees>
        </personnel>
        """
        
        if not clearance_level:
            return {"error": "clearance_level parameter required"}
        
        try:
            if not self.validate_clearance(clearance_level):
                return {"error": "Invalid or unauthorized clearance level"}
            
            sanitized_clearance = self.sanitize_input(clearance_level)
            
            root = ET.fromstring(xml_data)
            employees = []
            
            for emp in root.findall(".//employee"):
                if emp.get('clearance') == sanitized_clearance:
                    employees.append({
                        'name': emp.find('name').text,
                        'clearance': emp.get('clearance'),
                        'department': emp.find('department').text,
                        'status': emp.get('status')
                    })
            
            if not employees:
                return {"error": "No employees found for clearance level"}
            
            return {"employees": employees}
            
        except (ValueError, ET.ParseError) as e:
            return {"error": str(e)}

if __name__ == '__main__':
    cherrypy.config.update({
        'server.socket_host': '127.0.0.1',
        'server.socket_port': 8080,
        'log.screen': False
    })
    cherrypy.quickstart(SecureCherryPyApp())