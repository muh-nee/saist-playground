from flask import Flask, request, jsonify
from lxml import etree

app = Flask(__name__)

@app.route('/employee/<employee_id>')
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
    """
    
    root = etree.fromstring(xml_data)
    xpath_query = f"//employee[@id='{employee_id}']"
    result = root.xpath(xpath_query)
    
    if result:
        employee = result[0]
        return jsonify({
            'name': employee.find('name').text,
            'department': employee.get('department'),
            'salary': employee.find('salary').text,
            'clearance': employee.find('clearance').text
        })
    
    return jsonify({'error': 'Employee not found'}), 404

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)