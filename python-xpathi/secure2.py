
import xml.etree.ElementTree as ET
import re
import html
from enum import Enum

class SearchField(Enum):
    EQUALS = "eq"
    CONTAINS = "contains"
    GREATER_THAN = "gt"
    LESS_THAN = "lt"

class SecureElementTreeProcessor:
    
    def __init__(self):
        self.field_validators = {
            SearchField.PRODUCT_NAME: self._validate_product_name,
            SearchField.CATEGORY: self._validate_category,
            SearchField.PRICE_RANGE: self._validate_price,
            SearchField.STOCK_STATUS: self._validate_stock,
            SearchField.SUPPLIER: self._validate_supplier
        }
        
        self.allowed_categories = {
            'electronics', 'books', 'clothing', 'home', 'sports'
        }
        
        self.allowed_suppliers = {
            'TechCorp', 'BookPublisher', 'FashionCo', 'HomeMart', 'SportsCo'
        }
    
    def _validate_product_name(self, value):
        if not isinstance(value, str):
            return False, "Category must be a string"
        
        clean_value = value.lower().strip()
        if clean_value not in self.allowed_categories:
            return False, f"Invalid category. Allowed: {', '.join(self.allowed_categories)}"
        
        return True, "Valid"
    
    def _validate_price(self, value):
        try:
            stock = int(value)
            if stock < 0:
                return False, "Stock cannot be negative"
            if stock > 10000:
                return False, "Stock value too high"
            return True, "Valid"
        except (ValueError, TypeError):
            return False, "Stock must be a valid integer"
    
    def _validate_supplier(self, value):
        if not isinstance(value, str):
            return str(value)
        
        sanitized = html.escape(value)
        
        sanitized = sanitized.replace("'", "&
        sanitized = sanitized.replace('"', "&
        
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        return sanitized
    
    def validate_input(self, field, value):
    Secure product search using ElementTree with validation
    <catalog>
        <products>
            <product id="1" category="electronics">
                <name>Smartphone</name>
                <price>299.99</price>
                <stock>15</stock>
                <supplier>TechCorp</supplier>
                <description>Latest model smartphone with advanced features</description>
            </product>
            <product id="2" category="electronics">
                <name>Laptop</name>
                <price>899.99</price>
                <stock>8</stock>
                <supplier>TechCorp</supplier>
                <description>High-performance laptop for professionals</description>
            </product>
            <product id="3" category="books">
                <name>Security Guide</name>
                <price>49.99</price>
                <stock>25</stock>
                <supplier>BookPublisher</supplier>
                <description>Comprehensive security practices guide</description>
            </product>
            <product id="4" category="clothing">
                <name>Business Shirt</name>
                <price>79.99</price>
                <stock>50</stock>
                <supplier>FashionCo</supplier>
                <description>Professional business attire</description>
            </product>
        </products>
    </catalog>
    Safely check if a product matches the search criteria
    try:
        product_id = product.get('id', 'Unknown')
        category = product.get('category', 'Unknown')
        
        name_elem = product.find('name')
        price_elem = product.find('price')
        stock_elem = product.find('stock')
        supplier_elem = product.find('supplier')
        
        name = name_elem.text if name_elem is not None else 'Unknown'
        price = price_elem.text if price_elem is not None else 'Unknown'
        stock = stock_elem.text if stock_elem is not None else 'Unknown'
        supplier = supplier_elem.text if supplier_elem is not None else 'Unknown'
        
        print(f"  Product 
        print(f"    Category: {category}")
        print(f"    Price: ${price}")
        print(f"    Stock: {stock}")
        print(f"    Supplier: {supplier}")
        print()
        
    except Exception as e:
        print(f"Error displaying product: {e}")

def demonstrate_security_features(processor):
    print("=== Security Features Demonstration ===")
    
    print("\n1. Testing invalid field rejection:")
    secure_product_search(processor, "invalid_field", SearchOperator.EQUALS, "test")
    
    print("\n2. Testing invalid operator rejection:")
    secure_product_search(processor, SearchField.PRODUCT_NAME, "invalid_op", "test")
    
    print("\n3. Testing input validation:")
    secure_product_search(processor, SearchField.CATEGORY, SearchOperator.EQUALS, "invalid_category")
    
    print("\n4. Testing injection attempt (safely handled):")
    secure_product_search(processor, SearchField.PRODUCT_NAME, SearchOperator.CONTAINS, "'; DROP TABLE products; --")
    
    print("\n5. Testing price validation:")
    secure_product_search(processor, SearchField.PRICE_RANGE, SearchOperator.EQUALS, "not_a_number")

if __name__ == "__main__":
    print("=== Secure ElementTree XML Processing ===")
    
    processor = SecureElementTreeProcessor()
    
    print("Normal usage examples:")
    
    print("\n1. Search by product name:")
    secure_product_search(processor, SearchField.PRODUCT_NAME, SearchOperator.CONTAINS, "Smartphone")
    
    print("\n2. Search by category:")
    secure_product_search(processor, SearchField.CATEGORY, SearchOperator.EQUALS, "electronics")
    
    print("\n3. Search by price range:")
    secure_product_search(processor, SearchField.PRICE_RANGE, SearchOperator.LESS_THAN, "100")
    
    print("\n4. Search by stock level:")
    secure_product_search(processor, SearchField.STOCK_STATUS, SearchOperator.GREATER_THAN, "20")
    
    print("\n5. Search by supplier:")
    secure_product_search(processor, SearchField.SUPPLIER, SearchOperator.EQUALS, "TechCorp")
    
    demonstrate_security_features(processor)