from fastapi import FastAPI, HTTPException
import xmltodict
import xml.etree.ElementTree as ET
import uvicorn

app = FastAPI()

@app.get("/inventory/{item_category}")
async def vulnerable_inventory_search(item_category: str):
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
    
    root = ET.fromstring(xml_data)
    xpath_query = f"//item[@category='{item_category}']"
    result = root.findall(xpath_query)
    
    if not result:
        raise HTTPException(status_code=404, detail="No items found")
    
    items = []
    for item in result:
        items.append({
            'name': item.find('name').text,
            'category': item.get('category'),
            'price': item.find('price').text,
            'cost': item.find('cost').text,
            'supplier': item.find('supplier').text,
            'confidential': item.get('confidential')
        })
    
    return {"items": items}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)