import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class secure4 {
    private static final List&lt;String&gt; ALLOWED_STATUSES = Arrays.asList(
        "shipped", "pending", "delivered", "cancelled"
    );
    
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<orders>" +
                        "<order id='1'><customer>Alice</customer><total>250.00</total><status>shipped</status></order>" +
                        "<order id='2'><customer>Bob</customer><total>150.00</total><status>pending</status></order>" +
                        "<order id='3'><customer>Charlie</customer><total>300.00</total><status>delivered</status></order>" +
                        "</orders>";
        
        Builder parser = new Builder();
        Document doc = parser.build(new StringReader(xmlData));
        
        String userStatus = args.length > 0 ? args[0] : "shipped";
        
        if (!isValidStatus(userStatus)) {
            System.out.println("Error: Invalid status. Allowed statuses: " + ALLOWED_STATUSES);
            return;
        }
        
        Element root = doc.getRootElement();
        Elements orders = root.getChildElements("order");
        
        System.out.println("Status filter: " + userStatus);
        System.out.println("Using safe DOM traversal:");
        
        int customerCount = 0;
        for (int i = 0; i < orders.size(); i++) {
            Element order = orders.get(i);
            Element statusElement = order.getFirstChildElement("status");
            
            if (statusElement != null && userStatus.equals(statusElement.getValue())) {
                Element customerElement = order.getFirstChildElement("customer");
                if (customerElement != null) {
                    System.out.println("Customer: " + customerElement.getValue());
                    customerCount++;
                }
            }
        }
        
        System.out.println("Customers found: " + customerCount);
        
        if (args.length > 1) {
            String totalThreshold = args[1];
            
            if (isValidNumeric(totalThreshold)) {
                double threshold = Double.parseDouble(totalThreshold);
                System.out.println("\nSafe numeric filtering above: " + threshold);
                
                int numericCount = 0;
                for (int i = 0; i < orders.size(); i++) {
                    Element order = orders.get(i);
                    Element totalElement = order.getFirstChildElement("total");
                    
                    if (totalElement != null) {
                        try {
                            double total = Double.parseDouble(totalElement.getValue());
                            if (total > threshold) {
                                Element customerElement = order.getFirstChildElement("customer");
                                if (customerElement != null) {
                                    System.out.println("Customer with total > " + threshold + ": " + customerElement.getValue());
                                    numericCount++;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Warning: Invalid total format in order " + order.getAttributeValue("id"));
                        }
                    }
                }
                
                System.out.println("Results above threshold: " + numericCount);
            } else {
                System.out.println("Error: Invalid numeric threshold format.");
            }
        }
        
        if (args.length > 2) {
            String orderId = args[2];
            
            if (isValidOrderId(orderId)) {
                System.out.println("\nSafe order ID lookup:");
                
                boolean found = false;
                for (int i = 0; i < orders.size(); i++) {
                    Element order = orders.get(i);
                    String id = order.getAttributeValue("id");
                    
                    if (orderId.equals(id)) {
                        Element customerElement = order.getFirstChildElement("customer");
                        if (customerElement != null) {
                            System.out.println("Order ID " + orderId + " belongs to: " + customerElement.getValue());
                            found = true;
                        }
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Order ID " + orderId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid order ID format. Use numeric values only.");
            }
        }
    }
    
    private static boolean isValidStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_STATUSES.contains(status.trim().toLowerCase());
    }
    
    private static boolean isValidNumeric(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return NUMERIC_PATTERN.matcher(value.trim()).matches();
    }
    
    private static boolean isValidOrderId(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches("^\\d+$", orderId.trim());
    }
}