import defusedxml.ElementTree as ET
from defusedxml import defuse_stdlib
import xml.etree.ElementTree as stdlib_ET
import re
import html
from typing import Dict, List, Optional, Set, Union
from dataclasses import dataclass
from enum import Enum

defuse_stdlib()

class SecurityLevel(Enum):
    PUBLIC = "public"
    INTERNAL = "internal"
    CONFIDENTIAL = "confidential"
    RESTRICTED = "restricted"

@dataclass
class SecureSearchCriteria:
    element_type: str
    attribute_name: Optional[str]
    search_value: str
    required_security_level: SecurityLevel = SecurityLevel.PUBLIC

class SecureDefusedXMLProcessor:
    
    def __init__(self, user_security_clearance: SecurityLevel = SecurityLevel.PUBLIC):
        self.user_clearance = user_security_clearance
        
        self.clearance_hierarchy = {
            SecurityLevel.PUBLIC: 0,
            SecurityLevel.INTERNAL: 1,
            SecurityLevel.CONFIDENTIAL: 2,
            SecurityLevel.RESTRICTED: 3
        }
        
        self.allowed_elements = {
            'employee': SecurityLevel.INTERNAL,
            'document': SecurityLevel.CONFIDENTIAL,
            'project': SecurityLevel.INTERNAL,
            'task': SecurityLevel.PUBLIC,
            'user': SecurityLevel.INTERNAL,
            'system': SecurityLevel.CONFIDENTIAL,
            'report': SecurityLevel.INTERNAL,
            'configuration': SecurityLevel.RESTRICTED
        }
        
        self.allowed_attributes = {
            'id': SecurityLevel.PUBLIC,
            'name': SecurityLevel.PUBLIC,
            'type': SecurityLevel.PUBLIC,
            'status': SecurityLevel.INTERNAL,
            'priority': SecurityLevel.INTERNAL,
            'classification': SecurityLevel.CONFIDENTIAL,
            'clearance': SecurityLevel.CONFIDENTIAL,
            'security_level': SecurityLevel.RESTRICTED
        }
        
        self.sanitization_rules = {
            'strict': r'^[a-zA-Z0-9\s._-]{1,50}$',
            'alphanumeric': r'^[a-zA-Z0-9]{1,30}$',
            'text': r'^[a-zA-Z0-9\s.,!?-]{1,200}$',
            'identifier': r'^[a-zA-Z][a-zA-Z0-9_-]{0,29}$'
        }
        
        self.max_search_results = 25
        self.max_search_value_length = 100
    
    def has_security_clearance(self, required_level: SecurityLevel) -> bool:
        user_level = self.clearance_hierarchy.get(self.user_clearance, 0)
        required = self.clearance_hierarchy.get(required_level, 99)
        return user_level >= required
    
    def validate_element_access(self, element_type: str) -> tuple[bool, str]:
        if element_type not in self.allowed_elements:
            return False, f"Element type '{element_type}' not allowed"
        
        required_clearance = self.allowed_elements[element_type]
        if not self.has_security_clearance(required_clearance):
            return False, f"Insufficient clearance for element '{element_type}'"
        
        return True, "Valid"
    
    def validate_attribute_access(self, attribute_name: str) -> tuple[bool, str]:
        if attribute_name not in self.allowed_attributes:
            return False, f"Attribute '{attribute_name}' not allowed"
        
        required_clearance = self.allowed_attributes[attribute_name]
        if not self.has_security_clearance(required_clearance):
            return False, f"Insufficient clearance for attribute '{attribute_name}'"
        
        return True, "Valid"
    
    def sanitize_search_value(self, value: str, validation_type: str = 'strict') -> str:
        if not isinstance(value, str):
            value = str(value)
        
        sanitized = html.escape(value)
        
        sanitized = re.sub(r'[<>"\';(){}[\]\\|&$`]', '', sanitized)
        
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        if validation_type in self.sanitization_rules:
            pattern = self.sanitization_rules[validation_type]
            if not re.match(pattern, sanitized):
                sanitized = re.sub(r'[^a-zA-Z0-9\s._-]', '', sanitized)[:50]
        
        return sanitized[:self.max_search_value_length]
    
    def validate_search_criteria(self, criteria: SecureSearchCriteria) -> tuple[bool, str]:
        element_valid, element_msg = self.validate_element_access(criteria.element_type)
        if not element_valid:
            return False, element_msg
        
        if criteria.attribute_name:
            attr_valid, attr_msg = self.validate_attribute_access(criteria.attribute_name)
            if not attr_valid:
                return False, attr_msg
        
        if len(criteria.search_value) > self.max_search_value_length:
            return False, f"Search value too long. Maximum: {self.max_search_value_length}"
        
        if len(criteria.search_value.strip()) == 0:
            return False, "Search value cannot be empty"
        
        dangerous_patterns = [
            r'[<>]',           
            r'[\'"]',          
            r'[&|]',           
            r'[\(\)\[\]]',     
            r'[;]',            
            r'[@
        ]
        
        for pattern in dangerous_patterns:
            if re.search(pattern, criteria.search_value):
                return False, f"Search value contains dangerous pattern: {pattern}"
        
        if not self.has_security_clearance(criteria.required_security_level):
            return False, f"Insufficient clearance for security level: {criteria.required_security_level.value}"
        
        return True, "Valid"

def secure_employee_lookup(processor: SecureDefusedXMLProcessor, criteria: SecureSearchCriteria) -> None:
    xml_data = """
    <company security_level="internal">
        <employees>
            <employee id="emp001" status="active" clearance="internal" classification="standard">
                <name>Alice Johnson</name>
                <department>Engineering</department>
                <position>Software Engineer</position>
                <email>alice@company.com</email>
                <salary security_level="confidential">75000</salary>
                <ssn security_level="restricted">123-45-6789</ssn>
            </employee>
            <employee id="emp002" status="active" clearance="confidential" classification="senior">
                <name>Bob Smith</name>
                <department>Security</department>
                <position>Security Analyst</position>
                <email>bob@company.com</email>
                <salary security_level="confidential">85000</salary>
                <ssn security_level="restricted">987-65-4321</ssn>
            </employee>
            <employee id="emp003" status="inactive" clearance="public" classification="standard">
                <name>Carol Davis</name>
                <department>Marketing</department>
                <position>Marketing Coordinator</position>
                <email>carol@company.com</email>
                <salary security_level="confidential">55000</salary>
                <ssn security_level="restricted">456-78-9012</ssn>
            </employee>
        </employees>
    </company>
    <document_system security_level="confidential">
        <documents>
            <document id="doc001" type="report" status="published" classification="internal">
                <name>Quarterly Report</name>
                <title>Q4 2024 Financial Summary</title>
                <content>Revenue and expense analysis for Q4...</content>
                <author>Finance Team</author>
                <security_level>internal</security_level>
            </document>
            <document id="doc002" type="manual" status="draft" classification="confidential">
                <name>Security Manual</name>
                <title>Information Security Guidelines</title>
                <content>Comprehensive security policies and procedures...</content>
                <author>Security Team</author>
                <security_level>confidential</security_level>
            </document>
            <document id="doc003" type="specification" status="approved" classification="restricted">
                <name>System Architecture</name>
                <title>Core System Design Specification</title>
                <content>Detailed technical architecture documentation...</content>
                <author>Engineering Team</author>
                <security_level>restricted</security_level>
            </document>
        </documents>
    </document_system>
