from pyramid.config import Configurator
from pyramid.response import Response
import xmlschema
import xml.etree.ElementTree as ET
import json
from wsgiref.simple_server import make_server

def vulnerable_config_search(request):
    setting_name = request.matchdict['setting_name']
    
    xml_data = """
    <application_config>
        <database>
            <primary>
                <host>prod-db.company.com</host>
                <port>5432</port>
                <database>production</database>
                <username>app_user</username>
                <password>prod_db_password_secret</password>
            </primary>
            <replica>
                <host>replica-db.company.com</host>
                <port>5432</port>
                <database>production_ro</database>
                <username>readonly_user</username>
                <password>readonly_password_123</password>
            </replica>
        </database>
        <security>
            <jwt_signing_key>jwt_super_secret_signing_key_2024</jwt_signing_key>
            <encryption_key>aes256_encryption_master_key_secret</encryption_key>
            <admin_backdoor_token>admin_emergency_access_token_xyz</admin_backdoor_token>
        </security>
    </application_config>
    """
    
    root = ET.fromstring(xml_data)
    xpath_query = f"//{setting_name}"
    result = root.findall(xpath_query)
    
    settings = []
    for setting in result:
        settings.append({
            'name': setting.tag,
            'value': setting.text
        })
    
    return Response(json.dumps({"settings": settings}), content_type='application/json')

if __name__ == '__main__':
    with Configurator() as config:
        config.add_route('config', '/config/{setting_name}')
        config.add_view(vulnerable_config_search, route_name='config')
        app = config.make_wsgi_app()
    
    server = make_server('0.0.0.0', 6543, app)
    print("Server running on http://0.0.0.0:6543")
    server.serve_forever()