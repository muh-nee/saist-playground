
import xml.etree.ElementTree as ET
import xml.dom.minidom as minidom

class VulnerableFourSuiteStyle:
    
    def __init__(self, xml_source):
        self.dom = minidom.parseString(xml_source)
        self.etree_root = ET.fromstring(xml_source)
    
    def xpath_query(self, xpath_expression):
        print(f"4Suite-style XPath: {xpath_expression}")
        
        try:
            import lxml.etree as lxml_ET
            lxml_root = lxml_ET.fromstring(ET.tostring(self.etree_root))
            
            results = lxml_root.xpath(xpath_expression)
            return results
            
        except Exception as e:
            print(f"XPath evaluation error: {e}")
            return []
    
    def select_nodes(self, node_pattern, condition):
        xpath_expr = f"//{node_pattern}[{condition}]"
        return self.xpath_query(xpath_expr)
    
    def evaluate_expression(self, expression, context_node=None):
        if context_node is None:
            context_node = self.etree_root
            
        try:
            print(f"Evaluating expression: {expression}")
            
            context = {
                'node': context_node,
                'root': self.etree_root,
                'dom': self.dom
            }
            
            if hasattr(context_node, 'attrib'):
                for attr, value in context_node.attrib.items():
                    context[attr] = value
            
            if hasattr(context_node, 'text') and context_node.text:
                context['text'] = context_node.text.strip()
            
            result = eval(expression, {"__builtins__": {}}, context)
            return result
            
        except Exception as e:
            print(f"Expression evaluation error: {e}")
            return None

def vulnerable_inventory_system(item_query):
    xml_data = """
    <warehouse>
        <inventory>
            <item id="1" category="electronics" location="A1" restricted="false">
                <name>Consumer Laptop</name>
                <quantity>50</quantity>
                <value>45000</value>
                <supplier>TechDistributor</supplier>
            </item>
            <item id="2" category="electronics" location="S1" restricted="true">
                <name>Military Grade Equipment</name>
                <quantity>5</quantity>
                <value>500000</value>
                <supplier>DefenseContractor</supplier>
            </item>
            <item id="3" category="documents" location="V1" restricted="true">
                <name>Classified Documents</name>
                <quantity>1</quantity>
                <value>0</value>
                <supplier>Internal</supplier>
            </item>
            <item id="4" category="chemicals" location="H1" restricted="true">
                <name>Hazardous Materials</name>
                <quantity>10</quantity>
                <value>25000</value>
                <supplier>ChemCorp</supplier>
            </item>
        </inventory>
        <access_logs>
            <log timestamp="2024-01-15T10:00:00" user="operator1" action="view"/>
            <log timestamp="2024-01-15T10:30:00" user="admin" action="modify"/>
        </access_logs>
    </warehouse>
    <personnel>
        <employees>
            <employee id="1001" clearance="standard" status="active">
                <name>John Smith</name>
                <department>IT Support</department>
                <salary>55000</salary>
                <ssn>123-45-6789</ssn>
                <hire_date>2022-03-15</hire_date>
            </employee>
            <employee id="1002" clearance="secret" status="active">
                <name>Sarah Connor</name>
                <department>Cybersecurity</department>
                <salary>95000</salary>
                <ssn>987-65-4321</ssn>
                <hire_date>2021-07-01</hire_date>
            </employee>
            <employee id="1003" clearance="top_secret" status="suspended">
                <name>James Bond</name>
                <department>Special Operations</department>
                <salary>150000</salary>
                <ssn>007-00-7007</ssn>
                <hire_date>2020-01-01</hire_date>
            </employee>
        </employees>
        <contractors>
            <contractor id="2001" access_level="limited">
                <name>External Consultant</name>
                <company>ConsultCorp</company>
                <rate>200</rate>
                <project>Database Migration</project>
            </contractor>
        </contractors>
    </personnel>
    <system_config>
        <settings>
            <setting name="max_users" value="1000" type="integer"/>
            <setting name="admin_email" value="admin@company.com" type="string"/>
            <setting name="debug_mode" value="false" type="boolean"/>
            <setting name="api_key" value="sk_live_secret_key_12345" type="secret"/>
        </settings>
        <database>
            <connection_string>postgresql://user:password@localhost:5432/proddb</connection_string>
            <max_connections>100</max_connections>
            <timeout>30</timeout>
        </database>
    </system_config>
