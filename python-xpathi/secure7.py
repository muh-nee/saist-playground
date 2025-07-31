from bottle import route, run, request
import xml.etree.ElementTree as ET
import re
import html

class SecureBottleProcessor:
    
    def __init__(self):
        self.allowed_classifications = {
            'unclassified', 'internal', 'confidential', 'secret'
        }
        
        self.classification_patterns = {
            'unclassified': r'^unclassified$',
            'internal': r'^internal$',
            'confidential': r'^confidential$',
            'secret': r'^secret$'
        }
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 25:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w]', '', sanitized)
        
        return sanitized.lower()
    
    def validate_classification(self, classification):
        sanitized_class = self.sanitize_input(classification)
        
        if sanitized_class not in self.allowed_classifications:
            return False
        
        pattern = self.classification_patterns.get(sanitized_class)
        if pattern and not re.match(pattern, sanitized_class):
            return False
        
        return True

@route('/documents/<doc_classification>')
def secure_document_search(doc_classification):
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
                <title>Strategic Planning</title>
                <author>Executive Team</author>
                <content>Confidential strategic plans...</content>
                <access_level>management</access_level>
            </document>
            <document classification="secret" type="technical">
                <title>Security Architecture</title>
                <author>Security Team</author>
                <content>Security system specifications...</content>
                <access_level>security_team</access_level>
            </document>
        </documents>
    </document_system>
    """
    
    processor = SecureBottleProcessor()
    
    try:
        if not processor.validate_classification(doc_classification):
            return {"error": "Invalid or unauthorized classification"}
        
        sanitized_class = processor.sanitize_input(doc_classification)
        
        root = ET.fromstring(xml_data)
        documents = []
        
        for doc in root.findall(".//document"):
            if doc.get('classification') == sanitized_class:
                documents.append({
                    'title': doc.find('title').text,
                    'classification': doc.get('classification'),
                    'type': doc.get('type'),
                    'author': doc.find('author').text,
                    'access_level': doc.find('access_level').text
                })
        
        if not documents:
            return {"error": "No documents found for classification"}
        
        return {"documents": documents}
        
    except (ValueError, ET.ParseError) as e:
        return {"error": str(e)}

if __name__ == '__main__':
    run(host='127.0.0.1', port=8080, debug=False)