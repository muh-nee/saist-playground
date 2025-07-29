
import xml.etree.ElementTree as ET
from xml.dom import minidom

def vulnerable_schema_query(node_name, attribute_value):
    xml_data = """
    <company xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <employees>
            <employee id="1" clearance="public">
                <name>John Smith</name>
                <department>Marketing</department>
                <salary>50000</salary>
            </employee>
            <employee id="2" clearance="confidential">
                <name>Jane Doe</name>
                <department>Engineering</department>
                <salary>80000</salary>
            </employee>
            <employee id="3" clearance="secret">
                <name>Bob Wilson</name>
                <department>Security</department>
                <salary>95000</salary>
            </employee>
        </employees>
        <projects>
            <project classification="public">
                <name>Website Redesign</name>
                <budget>25000</budget>
            </project>
            <project classification="confidential">
                <name>New Product Launch</name>
                <budget>500000</budget>
            </project>
        </projects>
    </company>
    <system>
        <users>
            <user type="admin" status="active">
                <username>root</username>
                <password_hash>$2b$12$secrethash</password_hash>
                <last_login>2024-01-15</last_login>
            </user>
            <user type="regular" status="active">
                <username>guest</username>
                <password_hash>$2b$12$guesthash</password_hash>
                <last_login>2024-01-10</last_login>
            </user>
            <user type="service" status="disabled">
                <username>backup_service</username>
                <password_hash>$2b$12$servicehash</password_hash>
                <last_login>never</last_login>
            </user>
        </users>
        <logs>
            <entry level="info" timestamp="2024-01-15T10:00:00">
                <message>User login successful</message>
                <source>auth_service</source>
            </entry>
            <entry level="error" timestamp="2024-01-15T10:30:00">
                <message>Failed authentication attempt</message>
                <source>auth_service</source>
            </entry>
        </logs>
    </system>
