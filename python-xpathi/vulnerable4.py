from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.urls import path
from django.conf import settings
from django.core.wsgi import get_wsgi_application
import os
import defusedxml.ElementTree as ET

if not settings.configured:
    settings.configure(
        DEBUG=True,
        SECRET_KEY='django-insecure-key-for-demo',
        ROOT_URLCONF=__name__,
        ALLOWED_HOSTS=['*'],
    )

@csrf_exempt
def vulnerable_config_lookup(request, setting_name):
    xml_data = """
    <configuration>
        <database>
            <host>localhost</host>
            <port>5432</port>
            <username>dbuser</username>
            <password>dbpass123</password>
        </database>
        <api>
            <key>abc123def456</key>
            <secret>supersecretkey</secret>
            <endpoint>https://api.example.com</endpoint>
        </api>
        <security>
            <encryption>enabled</encryption>
            <audit_log>enabled</audit_log>
            <admin_token>admin_secret_token_xyz</admin_token>
        </security>
    </configuration>
    """
    
    root = ET.fromstring(xml_data)
    xpath_query = f"//{setting_name}"
    result = root.findall(xpath_query)
    
    settings_found = []
    for setting in result:
        settings_found.append({
            'name': setting.tag,
            'value': setting.text
        })
    
    return JsonResponse({'settings': settings_found})

urlpatterns = [
    path('config/<str:setting_name>/', vulnerable_config_lookup),
]

application = get_wsgi_application()

if __name__ == '__main__':
    from django.core.management import execute_from_command_line
    import sys
    sys.argv = ['vulnerable4.py', 'runserver', '0.0.0.0:8000']
    execute_from_command_line(sys.argv)