import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class secure6 {
    private static final List&lt;String&gt; ALLOWED_WAREHOUSES = Arrays.asList(
        "North", "South", "East", "West", "Central"
    );
    
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<inventory>" +
                        "<item id='1'><name>Widget A</name><quantity>100</quantity><warehouse>North</warehouse></item>" +
                        "<item id='2'><name>Widget B</name><quantity>50</quantity><warehouse>South</warehouse></item>" +
                        "<item id='3'><name>Widget C</name><quantity>75</quantity><warehouse>East</warehouse></item>" +
                        "</inventory>";
        
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xmlData)));
        Document document = parser.getDocument();
        
        String userWarehouse = args.length > 0 ? args[0] : "North";
        
        if (!isValidWarehouse(userWarehouse)) {
            System.out.println("Error: Invalid warehouse. Allowed warehouses: " + ALLOWED_WAREHOUSES);
            return;
        }
        
        NodeList items = document.getElementsByTagName("item");
        
        System.out.println("Warehouse filter: " + userWarehouse);
        System.out.println("Using safe DOM traversal:");
        
        int quantityCount = 0;
        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) item;
                
                NodeList warehouses = itemElement.getElementsByTagName("warehouse");
                if (warehouses.getLength() > 0) {
                    String warehouse = warehouses.item(0).getTextContent();
                    
                    if (userWarehouse.equals(warehouse)) {
                        NodeList quantities = itemElement.getElementsByTagName("quantity");
                        if (quantities.getLength() > 0) {
                            System.out.println("Quantity: " + quantities.item(0).getTextContent());
                            quantityCount++;
                        }
                    }
                }
            }
        }
        
        System.out.println("Quantities found: " + quantityCount);
        
        if (args.length > 1) {
            String namePrefix = args[1];
            
            if (isValidNamePrefix(namePrefix)) {
                System.out.println("\nSafe name filtering with prefix: " + namePrefix);
                
                int nameCount = 0;
                for (int i = 0; i < items.getLength(); i++) {
                    Node item = items.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        Element itemElement = (Element) item;
                        
                        NodeList names = itemElement.getElementsByTagName("name");
                        if (names.getLength() > 0) {
                            String name = names.item(0).getTextContent();
                            
                            if (name.startsWith(namePrefix)) {
                                System.out.println("Name: " + name);
                                nameCount++;
                            }
                        }
                    }
                }
                
                System.out.println("Names with prefix found: " + nameCount);
            } else {
                System.out.println("Error: Invalid name prefix format. Use alphanumeric characters only.");
            }
        }
        
        if (args.length > 2) {
            String minQuantity = args[2];
            
            if (isValidNumeric(minQuantity)) {
                int minQty = Integer.parseInt(minQuantity);
                System.out.println("\nSafe quantity filtering above: " + minQty);
                
                int rangeCount = 0;
                for (int i = 0; i < items.getLength(); i++) {
                    Node item = items.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        Element itemElement = (Element) item;
                        
                        NodeList quantities = itemElement.getElementsByTagName("quantity");
                        if (quantities.getLength() > 0) {
                            try {
                                int quantity = Integer.parseInt(quantities.item(0).getTextContent());
                                
                                if (quantity > minQty) {
                                    NodeList names = itemElement.getElementsByTagName("name");
                                    if (names.getLength() > 0) {
                                        System.out.println("Item: " + names.item(0).getTextContent() + 
                                                         " (Quantity: " + quantity + ")");
                                        rangeCount++;
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Warning: Invalid quantity format in item " + 
                                                 itemElement.getAttribute("id"));
                            }
                        }
                    }
                }
                
                System.out.println("Items above quantity threshold: " + rangeCount);
            } else {
                System.out.println("Error: Invalid quantity format. Use numeric values only.");
            }
        }
        
        if (args.length > 3) {
            String itemId = args[3];
            
            if (isValidNumeric(itemId)) {
                System.out.println("\nSafe item ID lookup:");
                
                boolean found = false;
                for (int i = 0; i < items.getLength(); i++) {
                    Node item = items.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        Element itemElement = (Element) item;
                        String id = itemElement.getAttribute("id");
                        
                        if (itemId.equals(id)) {
                            NodeList names = itemElement.getElementsByTagName("name");
                            if (names.getLength() > 0) {
                                System.out.println("Item ID " + itemId + ": " + names.item(0).getTextContent());
                                found = true;
                            }
                            break;
                        }
                    }
                }
                
                if (!found) {
                    System.out.println("Item ID " + itemId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid item ID format. Use numeric values only.");
            }
        }
    }
    
    private static boolean isValidWarehouse(String warehouse) {
        if (warehouse == null || warehouse.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_WAREHOUSES.contains(warehouse.trim());
    }
    
    private static boolean isValidNamePrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty() || prefix.length() > 20) {
            return false;
        }
        return Pattern.matches("^[a-zA-Z0-9\\s]+$", prefix.trim());
    }
    
    private static boolean isValidNumeric(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return NUMERIC_PATTERN.matcher(value.trim()).matches();
    }
}