import xml.etree.ElementTree as ET
from xml.dom import minidom
import re
import html
from typing import Dict, List, Optional, Set, Union
from dataclasses import dataclass
from enum import Enum

class QueryType(Enum):
    SIMPLE_MATCH = "simple_match"
    ATTRIBUTE_SEARCH = "attribute_search"
    TEXT_SEARCH = "text_search"
    ELEMENT_EXISTS = "element_exists"

@dataclass
class SecureQuery:
    query_type: QueryType
    element_name: str
    search_value: str
    attribute_name: Optional[str] = None

class SecureXMLSchemaProcessor:
    
    def __init__(self, schema_version: str = "1.0"):
        self.schema_version = schema_version
        
        self.allowed_elements = {
            'employee', 'user', 'project', 'task', 'document', 
            'configuration', 'setting', 'service', 'log'
        }
        
        self.allowed_attributes = {
            'id', 'name', 'type', 'status', 'level', 'category',
            'priority', 'version', 'active', 'enabled'
        }
        
        self.attribute_patterns = {
            'id': r'^[a-zA-Z0-9_-]{1,20}$',
            'name': r'^[a-zA-Z0-9\s._-]{1,50}$',
            'type': r'^[a-zA-Z]{1,20}$',
            'status': r'^(active|inactive|pending|completed|failed)$',
            'level': r'^(low|medium|high|critical)$',
            'category': r'^[a-zA-Z]{1,30}$',
            'priority': r'^[1-5]$',
            'version': r'^\d+\.\d+(\.\d+)?$'
        }
        
        self.max_search_length = 100
        self.max_results = 50
    
    def validate_element_name(self, element_name: str) -> tuple[bool, str]:
        if not isinstance(element_name, str):
            return False, "Element name must be a string"
        
        if element_name not in self.allowed_elements:
            return False, f"Element '{element_name}' not allowed. Allowed: {', '.join(self.allowed_elements)}"
        
        return True, "Valid"
    
    def validate_attribute_name(self, attribute_name: str) -> tuple[bool, str]:
        if not isinstance(attribute_name, str):
            return False, "Attribute name must be a string"
        
        if attribute_name not in self.allowed_attributes:
            return False, f"Attribute '{attribute_name}' not allowed. Allowed: {', '.join(self.allowed_attributes)}"
        
        return True, "Valid"
    
    def validate_search_value(self, search_value: str, attribute_name: Optional[str] = None) -> tuple[bool, str]:
        if not isinstance(search_value, str):
            return False, "Search value must be a string"
        
        if len(search_value) > self.max_search_length:
            return False, f"Search value too long. Maximum: {self.max_search_length}"
        
        if len(search_value.strip()) == 0:
            return False, "Search value cannot be empty"
        
        if attribute_name and attribute_name in self.attribute_patterns:
            pattern = self.attribute_patterns[attribute_name]
            if not re.match(pattern, search_value):
                return False, f"Search value doesn't match required pattern for attribute '{attribute_name}'"
        
        dangerous_chars = ['<', '>', '"', "'", '&', '|', ';', '(', ')', '[', ']', '=', '!']
        for char in dangerous_chars:
            if char in search_value:
                return False, f"Search value contains dangerous character: {char}"
        
        return True, "Valid"
    
    def sanitize_search_value(self, value: str) -> str:
        if not isinstance(value, str):
            value = str(value)
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w\s.-]', '', sanitized)
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        return sanitized[:self.max_search_length]

def secure_employee_search(processor: SecureXMLSchemaProcessor, query: SecureQuery) -> None:
    xml_data = """
    <company>
        <employees>
            <employee id="emp001" status="active" level="medium">
                <name>Alice Johnson</name>
                <department>Engineering</department>
                <position>Senior Developer</position>
                <clearance>internal</clearance>
            </employee>
            <employee id="emp002" status="active" level="high">
                <name>Bob Smith</name>
                <department>Security</department>
                <position>Security Analyst</position>
                <clearance>confidential</clearance>
            </employee>
            <employee id="emp003" status="inactive" level="low">
                <name>Carol Davis</name>
                <department>HR</department>
                <position>HR Coordinator</position>
                <clearance>public</clearance>
            </employee>
        </employees>
    </company>
    <projects>
        <project id="proj001" status="active" priority="3" category="development">
            <name>Website Redesign</name>
            <description>Modern responsive website</description>
            <manager>Alice Johnson</manager>
            <budget>50000</budget>
        </project>
        <project id="proj002" status="completed" priority="5" category="security">
            <name>Security Audit</name>
            <description>Comprehensive security review</description>
            <manager>Bob Smith</manager>
            <budget>25000</budget>
        </project>
        <project id="proj003" status="pending" priority="2" category="maintenance">
            <name>System Upgrade</name>
            <description>Infrastructure modernization</description>
            <manager>Carol Davis</manager>
            <budget>75000</budget>
        </project>
    </projects>
    <configuration version="2.1">
        <settings>
            <setting id="app_name" type="string" category="application">
                <name>Application Name</name>
                <value>SecureApp</value>
            </setting>
            <setting id="debug_mode" type="boolean" category="development">
                <name>Debug Mode</name>
                <value>false</value>
            </setting>
            <setting id="max_users" type="integer" category="limits">
                <name>Maximum Users</name>
                <value>1000</value>
            </setting>
            <setting id="log_level" type="string" category="logging">
                <name>Logging Level</name>
                <value>INFO</value>
            </setting>
        </settings>
    </configuration>
