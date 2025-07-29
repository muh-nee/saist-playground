
import xml.etree.ElementTree as ET

class VulnerableXMLParser:
    def __init__(self, xml_data):
        self.root = ET.fromstring(xml_data)
        self.data = xml_data
    
    def find_by_attribute(self, element_name, attr_name, attr_value):
        search_expression = f".//{element_name}[@{attr_name}='{attr_value}']"
        print(f"Search expression: {search_expression}")
        
        try:
            results = self.root.findall(search_expression)
            return results
        except Exception as e:
            print(f"Search error: {e}")
            return []
    
    def custom_filter(self, element_path, filter_code):
        elements = self.root.findall(element_path)
        results = []
        
        print(f"Applying custom filter: {filter_code}")
        
        for elem in elements:
            try:
                context = {
                    'element': elem,
                    'text': elem.text if elem.text else '',
                    'attrib': elem.attrib,
                    'tag': elem.tag
                }
                
                if eval(filter_code, {"__builtins__": {}}, context):
                    results.append(elem)
                    
            except Exception as e:
                print(f"Filter error for element {elem.tag}: {e}")
        
        return results

def vulnerable_product_catalog(category_filter):
    xml_data = """
    <catalog>
        <products>
            <product id="1" category="electronics" price="299.99">
                <name>Smartphone</name>
                <description>Latest model smartphone</description>
                <stock>15</stock>
                <supplier>TechCorp</supplier>
            </product>
            <product id="2" category="electronics" price="899.99">
                <name>Laptop</name>
                <description>High-performance laptop</description>
                <stock>8</stock>
                <supplier>CompuTech</supplier>
            </product>
            <product id="3" category="books" price="29.99">
                <name>Security Handbook</name>
                <description>Comprehensive security guide</description>
                <stock>25</stock>
                <supplier>BookCorp</supplier>
            </product>
            <product id="4" category="confidential" price="0.00">
                <name>Internal Document</name>
                <description>Company secrets</description>
                <stock>1</stock>
                <supplier>Internal</supplier>
            </product>
        </products>
    </catalog>
    <system>
        <users>
            <user id="1" role="admin" active="true">
                <username>administrator</username>
                <email>admin@company.com</email>
                <created>2024-01-01</created>
                <last_access>2024-01-15</last_access>
            </user>
            <user id="2" role="user" active="true">
                <username>john_doe</username>
                <email>john@company.com</email>
                <created>2024-01-05</created>
                <last_access>2024-01-14</last_access>
            </user>
            <user id="3" role="guest" active="false">
                <username>temp_guest</username>
                <email>guest@company.com</email>
                <created>2024-01-10</created>
                <last_access>2024-01-10</last_access>
            </user>
        </users>
    </system>
    <logs>
        <entry level="info" timestamp="2024-01-15T09:00:00" source="web">
            <message>User login successful</message>
            <user>john_doe</user>
            <ip>192.168.1.100</ip>
        </entry>
        <entry level="warning" timestamp="2024-01-15T09:15:00" source="web">
            <message>Multiple failed login attempts</message>
            <user>unknown</user>
            <ip>10.0.0.50</ip>
        </entry>
        <entry level="error" timestamp="2024-01-15T09:30:00" source="database">
            <message>Connection timeout</message>
            <user>system</user>
            <ip>localhost</ip>
        </entry>
        <entry level="critical" timestamp="2024-01-15T10:00:00" source="security">
            <message>Unauthorized access attempt detected</message>
            <user>attacker</user>
            <ip>external</ip>
        </entry>
    </logs>
