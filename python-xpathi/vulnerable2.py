
import xml.etree.ElementTree as ET

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
    <inventory>
        <item type="hardware">
            <name>Server</name>
            <confidential>true</confidential>
            <location>datacenter-1</location>
        </item>
        <item type="software">
            <name>Database License</name>
            <confidential>false</confidential>
            <location>cloud</location>
        </item>
    </inventory>
