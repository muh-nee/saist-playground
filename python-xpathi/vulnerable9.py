
import xml.etree.ElementTree as ET

class VulnerableLibxml2Wrapper:
    
    def __init__(self, xml_content):
        self.xml_content = xml_content
        self.etree_doc = ET.fromstring(xml_content)
        
        self.doc = self._create_doc_wrapper()
    
    def _create_doc_wrapper(self):
        return {
            'root': self.etree_doc,
            'content': self.xml_content
        }
    
    def xpath_eval(self, xpath_expression, context_node=None):
        print(f"libxml2 XPath evaluation: {xpath_expression}")
        
        try:
            import lxml.etree as lxml_ET
            
            lxml_doc = lxml_ET.fromstring(self.xml_content)
            
            if context_node:
                context = lxml_ET.XPathContext(lxml_doc)
                results = context(xpath_expression, context_node)
            else:
                results = lxml_doc.xpath(xpath_expression)
            
            return results
            
        except Exception as e:
            print(f"XPath evaluation error: {e}")
            return []
    
    def xpath_eval_expression(self, expr_string):
        print(f"libxml2 expression evaluation: {expr_string}")
        
        try:
            
            context = {
                'doc': self.doc,
                'root': self.etree_doc,
                '__builtins__': {}
            }
            
            context.update({
                'count': len,
                'string': str,
                'number': float,
                'boolean': bool
            })
            
            result = eval(expr_string, context)
            return result
            
        except Exception as e:
            print(f"Expression evaluation error: {e}")
            return None

def vulnerable_customer_database(customer_filter):
    xml_data = """
    <customer_database>
        <customers>
            <customer id="1001" status="active" tier="standard">
                <name>John Doe</name>
                <email>john@email.com</email>
                <credit_limit>5000</credit_limit>
                <ssn>123-45-6789</ssn>
                <payment_method>
                    <type>credit_card</type>
                    <number>4111-1111-1111-1111</number>
                    <expiry>12/25</expiry>
                </payment_method>
            </customer>
            <customer id="1002" status="active" tier="premium">
                <name>Jane Smith</name>
                <email>jane@email.com</email>
                <credit_limit>25000</credit_limit>
                <ssn>987-65-4321</ssn>
                <payment_method>
                    <type>bank_account</type>
                    <routing>021000021</routing>
                    <account>123456789</account>
                </payment_method>
            </customer>
            <customer id="1003" status="suspended" tier="vip">
                <name>Bob Wilson</name>
                <email>bob@email.com</email>
                <credit_limit>100000</credit_limit>
                <ssn>456-78-9012</ssn>
                <payment_method>
                    <type>corporate_account</type>
                    <account>CORP-ACCT-999</account>
                    <authorization>CEO-APPROVAL</authorization>
                </payment_method>
            </customer>
        </customers>
        <transactions>
            <transaction id="t001" customer_id="1001" amount="150.00" status="completed"/>
            <transaction id="t002" customer_id="1002" amount="2500.00" status="pending"/>
            <transaction id="t003" customer_id="1003" amount="50000.00" status="flagged"/>
        </transactions>
    </customer_database>
    <system_monitoring>
        <servers>
            <server id="web01" status="online" load="0.75">
                <hostname>web01.company.com</hostname>
                <ip>192.168.1.10</ip>
                <services>
                    <service name="nginx" port="80" status="running"/>
                    <service name="ssl" port="443" status="running"/>
                </services>
                <credentials>
                    <username>admin</username>
                    <password>web_admin_pass_123</password>
                </credentials>
            </server>
            <server id="db01" status="online" load="0.45">
                <hostname>db01.company.com</hostname>
                <ip>192.168.1.20</ip>
                <services>
                    <service name="postgresql" port="5432" status="running"/>
                    <service name="monitoring" port="9090" status="running"/>
                </services>
                <credentials>
                    <username>postgres</username>
                    <password>db_super_secret_password</password>
                </credentials>
            </server>
            <server id="backup01" status="maintenance" load="0.10">
                <hostname>backup01.company.com</hostname>
                <ip>192.168.1.30</ip>
                <services>
                    <service name="rsync" port="873" status="stopped"/>
                    <service name="ssh" port="22" status="running"/>
                </services>
                <credentials>
                    <username>backup_user</username>
                    <password>backup_system_key_xyz</password>
                </credentials>
            </server>
        </servers>
        <alerts>
            <alert level="warning" timestamp="2024-01-15T10:00:00">
                <message>High CPU usage on web01</message>
                <server>web01</server>
            </alert>
            <alert level="critical" timestamp="2024-01-15T10:30:00">
                <message>Database connection timeout</message>
                <server>db01</server>
            </alert>
        </alerts>
    </system_monitoring>
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
        <external_apis>
            <payment_gateway>
                <url>https://api.payment.com</url>
                <api_key>pk_live_secret_payment_key_xyz123</api_key>
                <webhook_secret>whsec_payment_webhook_secret</webhook_secret>
            </payment_gateway>
            <email_service>
                <url>https://api.mailservice.com</url>
                <api_key>mg_api_key_email_service_secret</api_key>
                <domain>mail.company.com</domain>
            </email_service>
        </external_apis>
        <security>
            <jwt_signing_key>jwt_super_secret_signing_key_2024</jwt_signing_key>
            <encryption_key>aes256_encryption_master_key_secret</encryption_key>
            <admin_backdoor_token>admin_emergency_access_token_xyz</admin_backdoor_token>
        </security>
    </application_config>
