from pyramid.config import Configurator
from pyramid.response import Response
import xml.etree.ElementTree as ET
import re
import html
import json
from wsgiref.simple_server import make_server

class SecurePyramidProcessor:
    
    def __init__(self):
        self.allowed_settings = {
            'host', 'port', 'database', 'username', 'timeout', 
            'level', 'destination', 'endpoint'
        }
        
        self.setting_patterns = {
            'host': r'^[a-zA-Z0-9.-]+$',
            'port': r'^\d{1,5}$',
            'timeout': r'^\d{1,3}$',
            'level': r'^(DEBUG|INFO|WARN|ERROR)$',
            'destination': r'^[a-zA-Z0-9_.-]+$'
        }
        
        self.max_input_length = 25
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > self.max_input_length:
            raise ValueError(f"Input too long. Maximum: {self.max_input_length}")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w.-]', '', sanitized)
        
        return sanitized.lower()
    
    def validate_setting_name(self, setting_name):
        sanitized_name = self.sanitize_input(setting_name)
        
        if sanitized_name not in self.allowed_settings:
            return False
        
        pattern = self.setting_patterns.get(sanitized_name)
        if pattern and not re.match(pattern, setting_name):
            return False
        
        return True

def secure_config_search(request):
    setting_name = request.matchdict['setting_name']
    
    xml_data = """
    <application_config>
        <database>
            <host>localhost</host>
            <port>5432</port>
            <database>production</database>
            <username>app_user</username>
            <timeout>30</timeout>
        </database>
        <logging>
            <level>INFO</level>
            <destination>file</destination>
        </logging>
        <api>
            <endpoint>https://api.example.com</endpoint>
            <timeout>60</timeout>
        </api>
    </application_config>
    """
    
    processor = SecurePyramidProcessor()
    
    try:
        if not processor.validate_setting_name(setting_name):
            return Response(
                json.dumps({"error": "Invalid or unauthorized setting name"}),
                status=400,
                content_type='application/json'
            )
        
        sanitized_name = processor.sanitize_input(setting_name)
        
        root = ET.fromstring(xml_data)
        settings = []
        
        for element in root.iter():
            if element.tag == sanitized_name and element.text:
                settings.append({
                    'name': element.tag,
                    'value': element.text
                })
        
        if not settings:
            return Response(
                json.dumps({"error": "No settings found"}),
                status=404,
                content_type='application/json'
            )
        
        return Response(
            json.dumps({"settings": settings}),
            content_type='application/json'
        )
        
    except (ValueError, ET.ParseError) as e:
        return Response(
            json.dumps({"error": str(e)}),
            status=500,
            content_type='application/json'
        )

if __name__ == '__main__':
    with Configurator() as config:
        config.add_route('config', '/config/{setting_name}')
        config.add_view(secure_config_search, route_name='config')
        app = config.make_wsgi_app()
    
    server = make_server('127.0.0.1', 6543, app)
    print("Server running on http://127.0.0.1:6543")
    server.serve_forever()