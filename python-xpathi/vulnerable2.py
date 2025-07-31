import xml.etree.ElementTree as ET
import sys

def vulnerable_product_search(product_name):
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
    
    root = ET.fromstring(xml_data)
    xpath_query = f".//product[name='{product_name}']"
    result = root.findall(xpath_query)
    
    products = []
    for product in result:
        products.append({
            'name': product.find('name').text,
            'price': product.find('price').text,
            'stock': product.find('stock').text
        })
    
    return products

if __name__ == "__main__":
    if len(sys.argv) > 1:
        user_input = sys.argv[1]
        results = vulnerable_product_search(user_input)
        if results:
            for product in results:
                print(f"Product: {product}")
        else:
            print("No products found")
    else:
        print("Usage: python vulnerable2.py <product_name>")