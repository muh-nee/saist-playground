import xml.etree.ElementTree as ET
import re
import html
from typing import Dict, List, Any, Optional, Callable
from dataclasses import dataclass
from enum import Enum

class FilterOperation(Enum):
    EQUALS = "equals"
    CONTAINS = "contains"
    STARTS_WITH = "starts_with"
    ENDS_WITH = "ends_with"
    GREATER_THAN = "gt"
    LESS_THAN = "lt"

@dataclass
class SecureFilter:
    field_name: str
    operation: FilterOperation
    value: str

class SecureUntangleStyleProcessor:
    
    def __init__(self):
        self.allowed_element_types = {
            'product', 'user', 'order', 'category', 'supplier',
            'customer', 'item', 'service', 'document', 'report'
        }
        
        self.allowed_attributes = {
            'id', 'name', 'type', 'category', 'status', 'priority',
            'level', 'version', 'active', 'enabled', 'visible'
        }
        
        self.allowed_child_elements = {
            'name', 'description', 'title', 'content', 'value',
            'price', 'quantity', 'stock', 'email', 'phone',
            'address', 'date', 'time', 'notes', 'tags'
        }
        
        self.max_filter_depth = 3
        self.max_results = 100
        self.max_string_length = 200
        
        self.type_validators = {
            'integer': self._validate_integer,
            'float': self._validate_float,
            'boolean': self._validate_boolean,
            'email': self._validate_email,
            'phone': self._validate_phone,
            'date': self._validate_date
        }
    
    def _validate_integer(self, value: str) -> bool:
        try:
            int_val = int(value)
            return -999999 <= int_val <= 999999
        except ValueError:
            return False
    
    def _validate_float(self, value: str) -> bool:
        try:
            float_val = float(value)
            return -999999.99 <= float_val <= 999999.99
        except ValueError:
            return False
    
    def _validate_boolean(self, value: str) -> bool:
        return value.lower() in ['true', 'false', '1', '0', 'yes', 'no']
    
    def _validate_email(self, value: str) -> bool:
        email_pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
        return bool(re.match(email_pattern, value)) and len(value) <= 100
    
    def _validate_phone(self, value: str) -> bool:
        phone_pattern = r'^\+?[\d\s\-\(\)]{7,20}$'
        return bool(re.match(phone_pattern, value))
    
    def _validate_date(self, value: str) -> bool:
        date_pattern = r'^\d{4}-\d{2}-\d{2}$'
        return bool(re.match(date_pattern, value))
    
    def validate_element_type(self, element_type: str) -> tuple[bool, str]:
        if not isinstance(element_type, str):
            return False, "Element type must be a string"
        
        if element_type.lower() not in self.allowed_element_types:
            return False, f"Element type '{element_type}' not allowed"
        
        return True, "Valid"
    
    def validate_filter(self, filter_obj: SecureFilter) -> tuple[bool, str]:
        if not isinstance(filter_obj.field_name, str):
            return False, "Filter field name must be a string"
        
        if filter_obj.field_name not in self.allowed_attributes and filter_obj.field_name not in self.allowed_child_elements:
            return False, f"Filter field '{filter_obj.field_name}' not allowed"
        
        if not isinstance(filter_obj.operation, FilterOperation):
            return False, "Filter operation must be a FilterOperation enum"
        
        if not isinstance(filter_obj.value, str):
            return False, "Filter value must be a string"
        
        if len(filter_obj.value) > self.max_string_length:
            return False, f"Filter value too long. Maximum: {self.max_string_length}"
        
        if len(filter_obj.value.strip()) == 0:
            return False, "Filter value cannot be empty"
        
        dangerous_chars = ['<', '>', '"', "'", '&', ';', '(', ')', '=', '!', '|']
        for char in dangerous_chars:
            if char in filter_obj.value:
                return False, f"Filter value contains dangerous character: {char}"
        
        return True, "Valid"
    
    def sanitize_string(self, value: str) -> str:
        if not isinstance(value, str):
            value = str(value)
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w\s.-@+]', '', sanitized)
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        return sanitized[:self.max_string_length]

def secure_product_search(processor: SecureUntangleStyleProcessor, element_type: str, filters: List[SecureFilter]) -> None:
    xml_data = """
    <catalog>
        <products>
            <product id="prod001" category="electronics" status="active" priority="high">
                <name>Wireless Headphones</name>
                <description>Premium noise-canceling headphones</description>
                <price>199.99</price>
                <stock>25</stock>
                <supplier>AudioTech</supplier>
                <tags>wireless, audio, premium</tags>
            </product>
            <product id="prod002" category="electronics" status="active" priority="medium">
                <name>Smartphone</name>
                <description>Latest generation smartphone</description>
                <price>699.99</price>
                <stock>15</stock>
                <supplier>MobileCorp</supplier>
                <tags>mobile, communication, smart</tags>
            </product>
            <product id="prod003" category="books" status="active" priority="low">
                <name>Python Programming Guide</name>
                <description>Comprehensive Python programming book</description>
                <price>39.99</price>
                <stock>50</stock>
                <supplier>BookPublisher</supplier>
                <tags>programming, education, python</tags>
            </product>
            <product id="prod004" category="electronics" status="inactive" priority="low">
                <name>Old Radio</name>
                <description>Vintage radio receiver</description>
                <price>29.99</price>
                <stock>2</stock>
                <supplier>VintageElectronics</supplier>
                <tags>vintage, radio, collectible</tags>
            </product>
        </products>
    </catalog>
    <system>
        <users>
            <user id="user001" status="active" level="admin" enabled="true">
                <name>System Administrator</name>
                <email>admin@company.com</email>
                <phone>+1-555-0101</phone>
                <date>2024-01-15</date>
                <notes>Primary system administrator</notes>
            </user>
            <user id="user002" status="active" level="user" enabled="true">
                <name>John Developer</name>
                <email>john@company.com</email>
                <phone>+1-555-0102</phone>
                <date>2024-01-16</date>
                <notes>Software developer</notes>
            </user>
            <user id="user003" status="inactive" level="guest" enabled="false">
                <name>Guest User</name>
                <email>guest@company.com</email>
                <phone>+1-555-0103</phone>
                <date>2024-01-10</date>
                <notes>Temporary guest account</notes>
            </user>
        </users>
    </system>
    <reports>
        <report id="rpt001" type="financial" status="completed" priority="high">
            <title>Quarterly Financial Report</title>
            <description>Q4 financial summary and analysis</description>
            <date>2024-01-31</date>
            <value>95.5</value>
            <notes>Revenue increased by 15%</notes>
        </report>
        <report id="rpt002" type="security" status="pending" priority="critical">
            <title>Security Audit Report</title>
            <description>Comprehensive security assessment</description>
            <date>2024-02-15</date>
            <value>87.2</value>
            <notes>Several vulnerabilities identified</notes>
        </report>
        <report id="rpt003" type="operational" status="draft" priority="medium">
            <title>Operational Efficiency Report</title>
            <description>Process optimization recommendations</description>
            <date>2024-02-01</date>
            <value>78.9</value>
            <notes>Recommendations for improvement</notes>
        </report>
    </reports>
