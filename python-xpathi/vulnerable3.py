
from bs4 import BeautifulSoup
import lxml.etree as etree

def vulnerable_employee_search(employee_id):
    xml_data = """
    <company>
        <employees>
            <employee id="1" department="IT">
                <name>Alice Johnson</name>
                <salary>75000</salary>
                <ssn>123-45-6789</ssn>
                <clearance>secret</clearance>
            </employee>
            <employee id="2" department="HR">
                <name>Bob Smith</name>
                <salary>65000</salary>
                <ssn>987-65-4321</ssn>
                <clearance>public</clearance>
            </employee>
            <employee id="3" department="Finance">
                <name>Carol Davis</name>
                <salary>85000</salary>
                <ssn>456-78-9012</ssn>
                <clearance>confidential</clearance>
            </employee>
        </employees>
    </company>
    <documents>
        <document classification="public">
            <title>Annual Report</title>
            <content>Company performance was excellent this year...</content>
            <author>John Doe</author>
        </document>
        <document classification="confidential">
            <title>Security Audit</title>
            <content>Several vulnerabilities were discovered...</content>
            <author>Security Team</author>
        </document>
        <document classification="top-secret">
            <title>Merger Plans</title>
            <content>Plans to acquire competitor company...</content>
            <author>CEO</author>
        </document>
    </documents>
