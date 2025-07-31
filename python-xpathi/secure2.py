import xml.etree.ElementTree as ET
import re
import html
import sys
from enum import Enum

class SearchOperator(Enum):
    EQUALS = "equals"
    CONTAINS = "contains"
    STARTS_WITH = "starts_with"
    ENDS_WITH = "ends_with"

class SecureElementTreeProcessor:
    
    def __init__(self):
        self.allowed_categories = {
            'electronics', 'books', 'clothing', 'home', 'sports'
        }
        
        self.allowed_fields = {
            'name', 'category', 'price', 'stock', 'supplier'
        }
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > 100:
            raise ValueError("Input too long")
        
        sanitized = html.escape(value)
        sanitized = sanitized.replace("'", "")
        sanitized = sanitized.replace('"', "")
        sanitized = re.sub(r'[^\w\s.-]', '', sanitized)
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        return sanitized
    
    def validate_category(self, category):
        clean_category = category.lower().strip()
        return clean_category in self.allowed_categories
    
    def validate_price(self, price_str):
        try:
            price = float(price_str)
            return 0 <= price <= 10000
        except (ValueError, TypeError):
            return False
    
    def validate_stock(self, stock_str):
        try:
            stock = int(stock_str)
            return 0 <= stock <= 1000
        except (ValueError, TypeError):
            return False

def secure_product_search(product_name):
    xml_data = """
    <products>
        <product id="1" category="electronics">
            <name>Laptop</name>
            <price>999.99</price>
            <stock>10</stock>
        </product>
        <product id="2" category="electronics">
            <name>Phone</name>
            <price>599.99</price>
            <stock>25</stock>
        </product>
        <product id="3" category="books">
            <name>Security Manual</name>
            <price>49.99</price>
            <stock>5</stock>
        </product>
    </products>
    """
    
    processor = SecureElementTreeProcessor()
    
    try:
        sanitized_name = processor.sanitize_input(product_name)
        
        if not re.match(r'^[a-zA-Z0-9\s.-]+$', sanitized_name):
            raise ValueError("Invalid characters in product name")
        
        root = ET.fromstring(xml_data)
        products = []
        
        for product in root.findall(".//product"):
            name_elem = product.find('name')
            if name_elem is not None and sanitized_name.lower() in name_elem.text.lower():
                products.append({
                    'name': name_elem.text,
                    'price': product.find('price').text,
                    'stock': product.find('stock').text,
                    'category': product.get('category')
                })
        
        return products
        
    except (ValueError, ET.ParseError) as e:
        print(f"Error: {e}")
        return []

if __name__ == "__main__":
    if len(sys.argv) > 1:
        user_input = sys.argv[1]
        results = secure_product_search(user_input)
        if results:
            for product in results:
                print(f"Product: {product}")
        else:
            print("No products found or invalid input")
    else:
        print("Usage: python secure2.py <product_name>")