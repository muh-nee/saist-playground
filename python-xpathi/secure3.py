from flask import Flask, request, jsonify
from lxml import etree
import re
import html

app = Flask(__name__)

class SecureFlaskProcessor:
    
    def __init__(self):
        self.allowed_departments = {
            'IT', 'HR', 'Finance', 'Engineering', 'Marketing'
        }
        
        self.allowed_employee_ids = {
            '1', '2', '3', '4', '5'
        }
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 20:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w-]', '', sanitized)
        
        return sanitized
    
    def validate_employee_id(self, employee_id):
        sanitized_id = self.sanitize_input(employee_id)
        return sanitized_id in self.allowed_employee_ids

@app.route('/employee/<employee_id>')
def secure_employee_search(employee_id):
    xml_data = """
    <company>
        <employees>
            <employee id="1" department="IT">
                <name>Alice Johnson</name>
                <salary>75000</salary>
                <clearance>secret</clearance>
            </employee>
            <employee id="2" department="HR">
                <name>Bob Smith</name>
                <salary>65000</salary>
                <clearance>public</clearance>
            </employee>
            <employee id="3" department="Finance">
                <name>Carol Davis</name>
                <salary>85000</salary>
                <clearance>confidential</clearance>
            </employee>
        </employees>
    </company>
    """
    
    processor = SecureFlaskProcessor()
    
    try:
        if not processor.validate_employee_id(employee_id):
            return jsonify({'error': 'Invalid employee ID'}), 400
        
        sanitized_id = processor.sanitize_input(employee_id)
        
        root = etree.fromstring(xml_data)
        
        for emp_elem in root.xpath("//employee"):
            if emp_elem.get('id') == sanitized_id:
                return jsonify({
                    'name': emp_elem.find('name').text,
                    'department': emp_elem.get('department'),
                    'clearance': emp_elem.find('clearance').text
                })
        
        return jsonify({'error': 'Employee not found'}), 404
        
    except (ValueError, etree.XPathEvalError) as e:
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(debug=False, host='127.0.0.1', port=5000)