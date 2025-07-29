import xml.etree.ElementTree as ET
import xml.dom.minidom as minidom
import re
import html
from typing import Dict, List, Any, Optional, Callable, Union
from dataclasses import dataclass
from enum import Enum

class QueryOperationType(Enum):
    SELECT = "select"
    FILTER = "filter"
    AGGREGATE = "aggregate"
    EXISTS = "exists"

class ComparisonOperator(Enum):
    EQUALS = "eq"
    NOT_EQUALS = "ne"
    CONTAINS = "contains"
    STARTS_WITH = "starts_with"
    ENDS_WITH = "ends_with"
    GREATER_THAN = "gt"
    LESS_THAN = "lt"

@dataclass
class SecureQueryClause:
    field_name: str
    operator: ComparisonOperator
    value: str
    field_type: str = "string"

@dataclass 
class SecureQueryBuilder:
    operation_type: QueryOperationType
    target_element: str
    clauses: List[SecureQueryClause]
    limit: int = 50

class Secure4SuiteStyleProcessor:
    
    def __init__(self):
        self.allowed_elements = {
            'inventory', 'item', 'product', 'order', 'customer',
            'supplier', 'category', 'warehouse', 'shipment', 'invoice'
        }
        
        self.allowed_fields = {
            'id', 'name', 'title', 'description', 'type', 'category',
            'status', 'priority', 'quantity', 'price', 'value',
            'date', 'timestamp', 'location', 'reference'
        }
        
        self.field_type_validators = {
            'string': self._validate_string,
            'integer': self._validate_integer,
            'float': self._validate_float,
            'date': self._validate_date,
            'boolean': self._validate_boolean,
            'enum': self._validate_enum
        }
        
        self.max_query_clauses = 10
        self.max_field_length = 100
        self.max_results = 100
        
        self.query_cache = {}
        self.cache_max_size = 50
    
    def _validate_string(self, value: str) -> tuple[bool, str]:
        if not isinstance(value, str):
            return False, "Value must be a string"
        
        if len(value) > self.max_field_length:
            return False, f"String too long. Maximum: {self.max_field_length}"
        
        if not re.match(r'^[a-zA-Z0-9\s._-]+$', value):
            return False, "String contains invalid characters"
        
        return True, "Valid"
    
    def _validate_integer(self, value: str) -> tuple[bool, str]:
        try:
            int_val = int(value)
            if not (-999999 <= int_val <= 999999):
                return False, "Integer out of range"
            return True, "Valid"
        except ValueError:
            return False, "Invalid integer format"
    
    def _validate_float(self, value: str) -> tuple[bool, str]:
        try:
            float_val = float(value)
            if not (-999999.99 <= float_val <= 999999.99):
                return False, "Float out of range"
            return True, "Valid"
        except ValueError:
            return False, "Invalid float format"
    
    def _validate_date(self, value: str) -> tuple[bool, str]:
        date_pattern = r'^\d{4}-\d{2}-\d{2}$'
        if not re.match(date_pattern, value):
            return False, "Invalid date format. Use YYYY-MM-DD"
        return True, "Valid"
    
    def _validate_boolean(self, value: str) -> tuple[bool, str]:
        if value.lower() in ['true', 'false', '1', '0', 'yes', 'no']:
            return True, "Valid"
        return False, "Invalid boolean value"
    
    def _validate_enum(self, value: str) -> tuple[bool, str]:
        allowed_enum_values = {
            'active', 'inactive', 'pending', 'completed', 'cancelled',
            'high', 'medium', 'low', 'critical', 'normal'
        }
        if value.lower() in allowed_enum_values:
            return True, "Valid"
        return False, f"Invalid enum value. Allowed: {', '.join(allowed_enum_values)}"
    
    def validate_query_builder(self, query_builder: SecureQueryBuilder) -> tuple[bool, str]:
        if query_builder.target_element not in self.allowed_elements:
            return False, f"Target element '{query_builder.target_element}' not allowed"
        
        if len(query_builder.clauses) > self.max_query_clauses:
            return False, f"Too many query clauses. Maximum: {self.max_query_clauses}"
        
        if query_builder.limit > self.max_results:
            return False, f"Result limit too high. Maximum: {self.max_results}"
        
        for clause in query_builder.clauses:
            clause_valid, clause_msg = self.validate_query_clause(clause)
            if not clause_valid:
                return False, f"Invalid clause: {clause_msg}"
        
        return True, "Valid"
    
    def validate_query_clause(self, clause: SecureQueryClause) -> tuple[bool, str]:
        if clause.field_name not in self.allowed_fields:
            return False, f"Field '{clause.field_name}' not allowed"
        
        if clause.field_type not in self.field_type_validators:
            return False, f"Field type '{clause.field_type}' not supported"
        
        validator = self.field_type_validators[clause.field_type]
        value_valid, value_msg = validator(clause.value)
        if not value_valid:
            return False, f"Invalid value for field '{clause.field_name}': {value_msg}"
        
        return True, "Valid"
    
    def sanitize_value(self, value: str, field_type: str) -> str:
        if not isinstance(value, str):
            value = str(value)
        
        sanitized = html.escape(value)
        
        if field_type == 'string':
            sanitized = re.sub(r'[^\w\s._-]', '', sanitized)
        elif field_type in ['integer', 'float']:
            sanitized = re.sub(r'[^\d.-]', '', sanitized)
        elif field_type == 'date':
            sanitized = re.sub(r'[^\d-]', '', sanitized)
        elif field_type == 'boolean':
            sanitized = re.sub(r'[^\w]', '', sanitized).lower()
        
        return sanitized[:self.max_field_length]

def secure_inventory_query(processor: Secure4SuiteStyleProcessor, query_builder: SecureQueryBuilder) -> None:
    xml_data = """
    <warehouse>
        <inventory>
            <item id="item001" category="electronics" status="active" priority="high">
                <name>Wireless Mouse</name>
                <description>Ergonomic wireless computer mouse</description>
                <quantity>150</quantity>
                <price>29.99</price>
                <location>A1-B2</location>
                <date>2024-01-15</date>
            </item>
            <item id="item002" category="electronics" status="active" priority="medium">
                <name>USB Keyboard</name>
                <description>Mechanical USB keyboard</description>
                <quantity>75</quantity>
                <price>89.99</price>
                <location>A1-B3</location>
                <date>2024-01-16</date>
            </item>
            <item id="item003" category="office" status="inactive" priority="low">
                <name>Office Chair</name>
                <description>Ergonomic office chair</description>
                <quantity>25</quantity>
                <price>199.99</price>
                <location>B2-C1</location>
                <date>2024-01-10</date>
            </item>
            <item id="item004" category="electronics" status="pending" priority="high">
                <name>Monitor Stand</name>
                <description>Adjustable monitor stand</description>
                <quantity>40</quantity>
                <price>59.99</price>
                <location>A2-B1</location>
                <date>2024-01-20</date>
            </item>
        </inventory>
    </warehouse>
    <business_data>
        <customers>
            <customer id="cust001" status="active" type="premium">
                <name>ABC Corporation</name>
                <category>enterprise</category>
                <value>50000.00</value>
                <date>2024-01-01</date>
            </customer>
            <customer id="cust002" status="active" type="standard">
                <name>XYZ Company</name>
                <category>small_business</category>
                <value>15000.00</value>
                <date>2024-01-15</date>
            </customer>
        </customers>
        <orders>
            <order id="ord001" status="completed" priority="high">
                <name>Office Equipment Order</name>
                <quantity>25</quantity>
                <value>2500.00</value>
                <date>2024-01-20</date>
                <reference>cust001</reference>
            </order>
            <order id="ord002" status="pending" priority="medium">
                <name>Software License Order</name>
                <quantity>10</quantity>
                <value>1200.00</value>
                <date>2024-01-22</date>
                <reference>cust002</reference>
            </order>
        </orders>
    </business_data>
