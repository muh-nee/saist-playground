from fastapi import FastAPI, HTTPException
import xml.etree.ElementTree as ET
import re
import html
import uvicorn

app = FastAPI()

class SecureFastAPIProcessor:
    
    def __init__(self):
        self.allowed_categories = {
            'electronics', 'books', 'software', 'clothing'
        }
        
        self.max_input_length = 30
        
        self.dangerous_patterns = [
            r'[\'"]',
            r'[<>]',
            r'[&|]',
            r'[\(\)\[\]]',
            r'[;@#$%]'
        ]
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > self.max_input_length:
            raise ValueError(f"Input too long. Maximum length: {self.max_input_length}")
        
        sanitized = html.escape(value)
        
        for pattern in self.dangerous_patterns:
            sanitized = re.sub(pattern, '', sanitized)
        
        sanitized = re.sub(r'\s+', ' ', sanitized.strip())
        
        if not re.match(r'^[a-zA-Z0-9\s_-]+$', sanitized):
            raise ValueError("Input contains invalid characters")
        
        return sanitized
    
    def validate_category(self, category):
        sanitized = self.sanitize_input(category)
        return sanitized.lower() in self.allowed_categories

@app.get("/inventory/{item_category}")
async def secure_inventory_search(item_category: str):
    xml_data = """
    <inventory>
        <items>
            <item category="electronics" confidential="true">
                <name>Laptop</name>
                <price>1200</price>
                <cost>800</cost>
                <supplier>SecretSupplier</supplier>
            </item>
            <item category="books" confidential="false">
                <name>Python Guide</name>
                <price>45</price>
                <cost>20</cost>
                <supplier>BookPublisher</supplier>
            </item>
            <item category="software" confidential="true">
                <name>Security Suite</name>
                <price>2500</price>
                <cost>1000</cost>
                <supplier>SecurityCorp</supplier>
            </item>
        </items>
    </inventory>
    """
    
    processor = SecureFastAPIProcessor()
    
    try:
        if not processor.validate_category(item_category):
            raise HTTPException(status_code=400, detail="Invalid or unauthorized category")
        
        sanitized_category = processor.sanitize_input(item_category)
        
        root = ET.fromstring(xml_data)
        items = []
        
        for item in root.findall(".//item"):
            if item.get('category') == sanitized_category.lower():
                items.append({
                    'name': item.find('name').text,
                    'category': item.get('category'),
                    'price': item.find('price').text
                })
        
        if not items:
            raise HTTPException(status_code=404, detail="No items found")
        
        return {"items": items}
        
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))
    except ET.ParseError as e:
        raise HTTPException(status_code=500, detail="XML parsing error")

if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)