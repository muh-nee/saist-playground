from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.urls import path
from django.conf import settings
from django.core.wsgi import get_wsgi_application
import defusedxml.ElementTree as ET
import re
import html

if not settings.configured:
    settings.configure(
        DEBUG=False,
        SECRET_KEY='django-secure-key-for-demo',
        ROOT_URLCONF=__name__,
        ALLOWED_HOSTS=['127.0.0.1', 'localhost'],
    )

class SecureDjangoProcessor:
    
    def __init__(self):
        self.allowed_settings = {
            'host', 'port', 'endpoint', 'timeout', 'level', 'destination'
        }
        
        self.setting_validators = {
            'host': self._validate_host,
            'port': self._validate_port,
            'timeout': self._validate_timeout,
            'level': self._validate_level
        }
    
    def _validate_host(self, value):
        return re.match(r'^[a-zA-Z0-9.-]+$', value) is not None
    
    def _validate_port(self, value):
        try:
            port = int(value)
            return 1 <= port <= 65535
        except (ValueError, TypeError):
            return False
    
    def _validate_timeout(self, value):
        try:
            timeout = int(value)
            return 1 <= timeout <= 300
        except (ValueError, TypeError):
            return False
    
    def _validate_level(self, value):
        return value.upper() in ['DEBUG', 'INFO', 'WARN', 'ERROR']
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 50:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w.-]', '', sanitized)
        
        return sanitized
    
    def validate_setting_name(self, setting_name):
        sanitized = self.sanitize_input(setting_name)
        return sanitized in self.allowed_settings

@csrf_exempt
def secure_config_lookup(request, setting_name):
    xml_data = """
    <configuration>
        <database>
            <host>localhost</host>
            <port>5432</port>
            <username>dbuser</username>
            <password>dbpass123</password>
        </database>
        <api>
            <endpoint>https://api.example.com</endpoint>
            <timeout>30</timeout>
        </api>
        <logging>
            <level>INFO</level>
            <destination>file</destination>
        </logging>
    </configuration>
    """
    
    processor = SecureDjangoProcessor()
    
    try:
        if not processor.validate_setting_name(setting_name):
            return JsonResponse({'error': 'Invalid setting name'}, status=400)
        
        sanitized_name = processor.sanitize_input(setting_name)
        
        root = ET.fromstring(xml_data)
        settings_found = []
        
        for element in root.iter():
            if element.tag == sanitized_name and element.text:
                if sanitized_name in processor.setting_validators:
                    if processor.setting_validators[sanitized_name](element.text):
                        settings_found.append({
                            'name': element.tag,
                            'value': element.text
                        })
                else:
                    settings_found.append({
                        'name': element.tag,
                        'value': element.text
                    })
        
        return JsonResponse({'settings': settings_found})
        
    except (ValueError, ET.ParseError) as e:
        return JsonResponse({'error': str(e)}, status=400)

urlpatterns = [
    path('config/<str:setting_name>/', secure_config_lookup),
]

application = get_wsgi_application()

if __name__ == '__main__':
    from django.core.management import execute_from_command_line
    import sys
    sys.argv = ['secure4.py', 'runserver', '127.0.0.1:8000']
    execute_from_command_line(sys.argv)