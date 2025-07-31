import cherrypy
import xml.dom.minidom
import xml.etree.ElementTree as ET

class VulnerableApp:
    
    @cherrypy.expose
    @cherrypy.tools.json_out()
    def employees(self, clearance_level=None):
        xml_data = """
        <personnel>
            <employees>
                <employee id="1001" clearance="standard" status="active">
                    <name>John Smith</name>
                    <department>IT Support</department>
                    <salary>55000</salary>
                    <ssn>123-45-6789</ssn>
                </employee>
                <employee id="1002" clearance="secret" status="active">
                    <name>Sarah Connor</name>
                    <department>Cybersecurity</department>
                    <salary>95000</salary>
                    <ssn>987-65-4321</ssn>
                </employee>
                <employee id="1003" clearance="top_secret" status="suspended">
                    <name>James Bond</name>
                    <department>Special Operations</department>
                    <salary>150000</salary>
                    <ssn>007-00-7007</ssn>
                </employee>
            </employees>
        </personnel>
        """
        
        if clearance_level:
            root = ET.fromstring(xml_data)
            xpath_query = f"//employee[@clearance='{clearance_level}']"
            result = root.findall(xpath_query)
            
            employees = []
            for emp in result:
                employees.append({
                    'name': emp.find('name').text,
                    'clearance': emp.get('clearance'),
                    'department': emp.find('department').text,
                    'salary': emp.find('salary').text,
                    'status': emp.get('status')
                })
            
            return {"employees": employees}
        
        return {"error": "clearance_level parameter required"}

if __name__ == '__main__':
    cherrypy.config.update({
        'server.socket_host': '0.0.0.0',
        'server.socket_port': 8080,
    })
    cherrypy.quickstart(VulnerableApp())