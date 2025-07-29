
import xmltodict
import xml.etree.ElementTree as ET

def vulnerable_config_lookup(setting_name):
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
    <users>
        <user role="admin" active="true">
            <id>1</id>
            <username>administrator</username>
            <email>admin@company.com</email>
            <permissions>all</permissions>
        </user>
        <user role="user" active="true">
            <id>2</id>
            <username>john_doe</username>
            <email>john@company.com</email>
            <permissions>read</permissions>
        </user>
        <user role="user" active="false">
            <id>3</id>
            <username>inactive_user</username>
            <email>inactive@company.com</email>
            <permissions>none</permissions>
        </user>
    </users>
    <inventory>
        <items>
            <item category="electronics">
                <name>Laptop</name>
                <price>1200</price>
                <sensitive_data>
                    <cost>800</cost>
                    <supplier>SecretSupplier</supplier>
                </sensitive_data>
            </item>
            <item category="books">
                <name>Python Guide</name>
                <price>45</price>
                <sensitive_data>
                    <cost>20</cost>
                    <supplier>BookPublisher</supplier>
                </sensitive_data>
            </item>
        </items>
    </inventory>
