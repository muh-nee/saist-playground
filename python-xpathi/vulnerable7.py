from bottle import route, run, request
from bs4 import BeautifulSoup
import xml.etree.ElementTree as ET

@route('/documents/<doc_classification>')
def vulnerable_document_search(doc_classification):
    xml_data = """
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
    """
    
    root = ET.fromstring(xml_data)
    xpath_query = f"//document[@classification='{doc_classification}']"
    result = root.findall(xpath_query)
    
    documents = []
    for doc in result:
        documents.append({
            'title': doc.find('title').text,
            'classification': doc.get('classification'),
            'type': doc.get('type'),
            'author': doc.find('author').text,
            'content': doc.find('content').text,
            'access_level': doc.find('access_level').text
        })
    
    return {"documents": documents}

if __name__ == '__main__':
    run(host='localhost', port=8080, debug=True)