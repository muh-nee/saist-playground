import xml.etree.ElementTree as ET
import re
import html
from typing import Dict, List, Any, Optional, Tuple, Union
from dataclasses import dataclass
from enum import Enum
import hashlib

class PreparedStatementType(Enum):
    SELECT = "select"
    FILTER = "filter"
    COUNT = "count"
    EXISTS = "exists"

@dataclass
class PreparedParameter:
    name: str
    value: str
    param_type: str
    validation_rule: Optional[str] = None

@dataclass
class PreparedStatement:
    statement_id: str
    statement_type: PreparedStatementType
    template: str
    parameters: List[PreparedParameter]
    max_results: int = 50

class SecureLibxml2StyleProcessor:
    
    def __init__(self):
        self.allowed_elements = {
            'database', 'table', 'record', 'field', 'index',
            'customer', 'order', 'product', 'transaction', 'user'
        }
        
        self.allowed_fields = {
            'id', 'name', 'title', 'description', 'value', 'type',
            'status', 'category', 'priority', 'date', 'timestamp',
            'reference', 'code', 'amount', 'quantity', 'price'
        }
        
        self.parameter_validators = {
            'string': self._validate_string_param,
            'integer': self._validate_integer_param,
            'float': self._validate_float_param,
            'date': self._validate_date_param,
            'boolean': self._validate_boolean_param,
            'uuid': self._validate_uuid_param,
            'email': self._validate_email_param
        }
        
        self.prepared_statements = {}
        self.max_statement_cache = 100
        self.max_param_length = 200
        self.max_results_limit = 1000
    
    def _validate_string_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        if not isinstance(value, str):
            return False, "Parameter must be a string"
        
        if len(value) > self.max_param_length:
            return False, f"String parameter too long. Maximum: {self.max_param_length}"
        
        if rule:
            if rule == 'alphanumeric':
                if not re.match(r'^[a-zA-Z0-9]+$', value):
                    return False, "String must be alphanumeric"
            elif rule == 'identifier':
                if not re.match(r'^[a-zA-Z][a-zA-Z0-9_-]*$', value):
                    return False, "String must be a valid identifier"
            elif rule == 'text':
                if not re.match(r'^[a-zA-Z0-9\s.,!?-]+$', value):
                    return False, "String contains invalid characters"
        
        dangerous_patterns = ['<script', '<?xml', '<!DOCTYPE', 'javascript:', 'vbscript:']
        for pattern in dangerous_patterns:
            if pattern.lower() in value.lower():
                return False, f"String contains dangerous pattern: {pattern}"
        
        return True, "Valid"
    
    def _validate_integer_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        try:
            int_val = int(value)
            
            if rule == 'positive' and int_val <= 0:
                return False, "Integer must be positive"
            elif rule == 'non_negative' and int_val < 0:
                return False, "Integer must be non-negative"
            elif rule == 'range_1_100' and not (1 <= int_val <= 100):
                return False, "Integer must be between 1 and 100"
            
            if not (-2147483648 <= int_val <= 2147483647):
                return False, "Integer out of range"
            
            return True, "Valid"
        except ValueError:
            return False, "Invalid integer format"
    
    def _validate_float_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        try:
            float_val = float(value)
            
            if rule == 'positive' and float_val <= 0.0:
                return False, "Float must be positive"
            elif rule == 'percentage' and not (0.0 <= float_val <= 100.0):
                return False, "Float must be a valid percentage (0-100)"
            
            return True, "Valid"
        except ValueError:
            return False, "Invalid float format"
    
    def _validate_date_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        date_patterns = {
            'iso': r'^\d{4}-\d{2}-\d{2}$',
            'us': r'^\d{2}/\d{2}/\d{4}$',
            'timestamp': r'^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$'
        }
        
        pattern = date_patterns.get(rule, date_patterns['iso'])
        if not re.match(pattern, value):
            return False, f"Invalid date format for rule: {rule or 'iso'}"
        
        return True, "Valid"
    
    def _validate_boolean_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        valid_booleans = {'true', 'false', '1', '0', 'yes', 'no', 'on', 'off'}
        if value.lower() not in valid_booleans:
            return False, f"Invalid boolean value. Allowed: {', '.join(valid_booleans)}"
        return True, "Valid"
    
    def _validate_uuid_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        uuid_pattern = r'^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$'
        if not re.match(uuid_pattern, value):
            return False, "Invalid UUID format"
        return True, "Valid"
    
    def _validate_email_param(self, value: str, rule: Optional[str] = None) -> Tuple[bool, str]:
        email_pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
        if not re.match(email_pattern, value) or len(value) > 100:
            return False, "Invalid email format"
        return True, "Valid"
    
    def create_prepared_statement(self, statement: PreparedStatement) -> Tuple[bool, str]:
        if len(self.prepared_statements) >= self.max_statement_cache:
            return False, "Statement cache full"
        
        if statement.statement_id in self.prepared_statements:
            return False, f"Statement ID '{statement.statement_id}' already exists"
        
        template_valid, template_msg = self._validate_statement_template(statement)
        if not template_valid:
            return False, template_msg
        
        for param in statement.parameters:
            param_valid, param_msg = self._validate_parameter(param)
            if not param_valid:
                return False, f"Invalid parameter '{param.name}': {param_msg}"
        
        self.prepared_statements[statement.statement_id] = statement
        return True, "Statement prepared successfully"
    
    def _validate_statement_template(self, statement: PreparedStatement) -> Tuple[bool, str]:
        template = statement.template
        
        if not template or len(template.strip()) == 0:
            return False, "Statement template cannot be empty"
        
        dangerous_keywords = ['DROP', 'DELETE', 'UPDATE', 'INSERT', 'CREATE', 'ALTER', 'EXEC']
        upper_template = template.upper()
        for keyword in dangerous_keywords:
            if keyword in upper_template:
                return False, f"Statement contains dangerous keyword: {keyword}"
        
        param_placeholders = re.findall(r'\$\{(\w+)\}', template)
        declared_params = {param.name for param in statement.parameters}
        
        for placeholder in param_placeholders:
            if placeholder not in declared_params:
                return False, f"Undeclared parameter placeholder: ${{{placeholder}}}"
        
        for declared_param in declared_params:
            if declared_param not in param_placeholders:
                return False, f"Declared parameter '{declared_param}' not used in template"
        
        return True, "Valid"
    
    def _validate_parameter(self, param: PreparedParameter) -> Tuple[bool, str]:
        if param.param_type not in self.parameter_validators:
            return False, f"Unsupported parameter type: {param.param_type}"
        
        validator = self.parameter_validators[param.param_type]
        return validator(param.value, param.validation_rule)
    
    def sanitize_parameter_value(self, param: PreparedParameter) -> str:
        if not isinstance(param.value, str):
            value = str(param.value)
        else:
            value = param.value
        
        sanitized = html.escape(value)
        
        if param.param_type == 'string':
            sanitized = re.sub(r'[<>"\';(){}[\]\\|&$`]', '', sanitized)
        elif param.param_type in ['integer', 'float']:
            sanitized = re.sub(r'[^\d.-]', '', sanitized)
        elif param.param_type == 'date':
            sanitized = re.sub(r'[^\d\-/: ]', '', sanitized)
        elif param.param_type == 'boolean':
            sanitized = re.sub(r'[^\w]', '', sanitized).lower()
        
        return sanitized[:self.max_param_length]

def secure_prepared_query_execution(processor: SecureLibxml2StyleProcessor, statement_id: str) -> None:
    xml_data = """
    <database>
        <customers>
            <customer id="cust001" status="active" type="premium">
                <name>Global Industries Ltd</name>
                <email>contact@global-industries.com</email>
                <category>enterprise</category>
                <date>2024-01-15</date>
                <amount>75000.50</amount>
                <reference>REF-001</reference>
            </customer>
            <customer id="cust002" status="active" type="standard">
                <name>Small Business Corp</name>
                <email>info@smallbiz.com</email>
                <category>small_business</category>
                <date>2024-01-20</date>
                <amount>12500.25</amount>
                <reference>REF-002</reference>
            </customer>
            <customer id="cust003" status="inactive" type="basic">
                <name>Startup Ventures</name>
                <email>hello@startup.com</email>
                <category>startup</category>
                <date>2024-01-10</date>
                <amount>5000.00</amount>
                <reference>REF-003</reference>
            </customer>
        </customers>
    </database>
