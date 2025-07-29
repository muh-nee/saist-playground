
import defusedxml.ElementTree as ET
from defusedxml import defuse_stdlib
import xml.etree.ElementTree as stdlib_ET

defuse_stdlib()

def vulnerable_secure_employee_lookup(employee_criteria):
    xml_data = """
    <enterprise>
        <employees>
            <employee id="1" clearance="public" department="hr">
                <name>Alice Johnson</name>
                <position>HR Manager</position>
                <salary>65000</salary>
                <social_security>123-45-6789</social_security>
            </employee>
            <employee id="2" clearance="secret" department="engineering">
                <name>Bob Smith</name>
                <position>Senior Engineer</position>
                <salary>95000</salary>
                <social_security>987-65-4321</social_security>
            </employee>
            <employee id="3" clearance="top-secret" department="research">
                <name>Carol Davis</name>
                <position>Research Director</position>
                <salary>120000</salary>
                <social_security>456-78-9012</social_security>
            </employee>
        </employees>
        <departments>
            <department name="hr" budget="500000"/>
            <department name="engineering" budget="2000000"/>
            <department name="research" budget="5000000"/>
        </departments>
    </enterprise>
    Document search using defusedxml with XPath injection vulnerability
    <document_system>
        <documents>
            <document classification="unclassified" type="report">
                <title>Annual Sales Report</title>
                <author>Sales Team</author>
                <content>Sales increased by 15% this year...</content>
                <access_level>public</access_level>
            </document>
            <document classification="confidential" type="memo">
                <title>Layoff Plans</title>
                <author>Executive Team</author>
                <content>We plan to reduce workforce by 20%...</content>
                <access_level>management</access_level>
            </document>
            <document classification="secret" type="technical">
                <title>Vulnerability Assessment</title>
                <author>Security Team</author>
                <content>Critical vulnerabilities found in system...</content>
                <access_level>security_team</access_level>
            </document>
        </documents>
    </document_system>
    Configuration lookup with defusedxml and XPath injection
    <configuration>
        <database>
            <host>production-db.company.com</host>
            <port>5432</port>
            <username>db_user</username>
            <password>super_secret_db_password</password>
            <ssl_cert>/etc/ssl/certs/db.pem</ssl_cert>
        </database>
        <api_keys>
            <service name="payment_gateway">
                <key>pk_live_secret_payment_key_12345</key>
                <webhook_secret>whsec_super_secret_webhook</webhook_secret>
            </service>
            <service name="email_service">
                <key>sg_api_key_secret_mailgun_key</key>
                <domain>mail.company.com</domain>
            </service>
        </api_keys>
        <security>
            <jwt_secret>jwt_super_secret_key_for_tokens</jwt_secret>
            <encryption_key>aes_256_encryption_master_key</encryption_key>
            <admin_token>admin_backdoor_token_xyz123</admin_token>
        </security>
    </configuration>
