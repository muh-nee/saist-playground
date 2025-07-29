import org.apache.xpath.XPathAPI;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import java.io.StringReader;

public class vulnerable6 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<inventory>" +
                        "<item id='1'><name>Widget A</name><quantity>100</quantity><warehouse>North</warehouse></item>" +
                        "<item id='2'><name>Widget B</name><quantity>50</quantity><warehouse>South</warehouse></item>" +
                        "<item id='3'><name>Widget C</name><quantity>75</warehouse>East</warehouse></item>" +
                        "</inventory>";
        
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xmlData)));
        Document document = parser.getDocument();
        
        String userWarehouse = args.length > 0 ? args[0] : "North' or '1'='1";
        
        String xpathQuery = "//item[warehouse='" + userWarehouse + "']/quantity/text()";
        
        NodeList quantities = XPathAPI.selectNodeList(document, xpathQuery);
        
        System.out.println("XPath Query: " + xpathQuery);
        System.out.println("Quantities found: " + quantities.getLength());
        
        for (int i = 0; i < quantities.getLength(); i++) {
            Node quantity = quantities.item(i);
            System.out.println("Quantity: " + quantity.getNodeValue());
        }
        
        String unionQuery = "//item[name='" + (args.length > 1 ? args[1] : "Widget A' union //item[1]/name | //") + "']/name/text()";
        NodeList names = XPathAPI.selectNodeList(document, unionQuery);
        
        System.out.println("\nUnion XPath Query: " + unionQuery);
        System.out.println("Names found: " + names.getLength());
        
        for (int i = 0; i < names.getLength(); i++) {
            Node name = names.item(i);
            System.out.println("Name: " + name.getNodeValue());
        }
    }
}