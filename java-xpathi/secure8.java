import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlCursor;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class secure8 {
    private static final List&lt;String&gt; ALLOWED_TYPES = Arrays.asList(
        "enterprise", "small", "medium", "startup"
    );
    
    private static final Pattern CREDIT_PATTERN = Pattern.compile("^\\d+$");
    
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<customers>" +
                        "<customer id='1'><name>Alice Corp</name><type>enterprise</type><credit>10000</credit></customer>" +
                        "<customer id='2'><name>Bob LLC</name><type>small</type><credit>5000</credit></customer>" +
                        "<customer id='3'><name>Charlie Inc</name><type>enterprise</type><credit>15000</credit></customer>" +
                        "</customers>";
        
        XmlObject xmlObj = XmlObject.Factory.parse(xmlData);
        
        String userType = args.length > 0 ? args[0] : "enterprise";
        
        if (!isValidType(userType)) {
            System.out.println("Error: Invalid customer type. Allowed types: " + ALLOWED_TYPES);
            return;
        }
        
        XmlObject[] allCustomers = xmlObj.selectPath("
        
        System.out.println("Customer type filter: " + userType);
        System.out.println("Using safe XPath with post-filtering:");
        
        int customerCount = 0;
        for (XmlObject customer : allCustomers) {
            XmlObject[] typeElements = customer.selectPath("type/text()");
            
            if (typeElements.length > 0) {
                XmlCursor typeCursor = typeElements[0].newCursor();
                String type = typeCursor.getTextValue();
                
                if (userType.equals(type)) {
                    XmlObject[] nameElements = customer.selectPath("name/text()");
                    if (nameElements.length > 0) {
                        XmlCursor nameCursor = nameElements[0].newCursor();
                        System.out.println("Customer: " + nameCursor.getTextValue());
                        customerCount++;
                        nameCursor.dispose();
                    }
                }
                typeCursor.dispose();
            }
        }
        
        System.out.println("Customers found: " + customerCount);
        
        if (args.length > 1) {
            String creditThreshold = args[1];
            
            if (isValidCredit(creditThreshold)) {
                int threshold = Integer.parseInt(creditThreshold);
                System.out.println("\nSafe credit filtering above: " + threshold);
                
                int creditCount = 0;
                for (XmlObject customer : allCustomers) {
                    XmlObject[] creditElements = customer.selectPath("credit/text()");
                    
                    if (creditElements.length > 0) {
                        XmlCursor creditCursor = creditElements[0].newCursor();
                        
                        try {
                            int credit = Integer.parseInt(creditCursor.getTextValue());
                            
                            if (credit > threshold) {
                                XmlObject[] nameElements = customer.selectPath("name/text()");
                                if (nameElements.length > 0) {
                                    XmlCursor nameCursor = nameElements[0].newCursor();
                                    System.out.println("Customer: " + nameCursor.getTextValue());
                                    creditCount++;
                                    nameCursor.dispose();
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Warning: Invalid credit format in customer data");
                        }
                        
                        creditCursor.dispose();
                    }
                }
                
                System.out.println("Customers above credit threshold: " + creditCount);
            } else {
                System.out.println("Error: Invalid credit format. Use numeric values only.");
            }
        }
        
        if (args.length > 2) {
            String customerId = args[2];
            
            if (isValidCustomerId(customerId)) {
                System.out.println("\nSafe customer ID lookup:");
                
                boolean found = false;
                for (XmlObject customer : allCustomers) {
                    XmlCursor cursor = customer.newCursor();
                    
                    if (cursor.toFirstAttribute()) {
                        do {
                            if ("id".equals(cursor.getName().getLocalPart())) {
                                String id = cursor.getTextValue();
                                
                                if (customerId.equals(id)) {
                                    cursor.dispose();
                                    
                                    XmlObject[] nameElements = customer.selectPath("name/text()");
                                    if (nameElements.length > 0) {
                                        XmlCursor nameCursor = nameElements[0].newCursor();
                                        System.out.println("Customer ID " + customerId + ": " + nameCursor.getTextValue());
                                        nameCursor.dispose();
                                        found = true;
                                    }
                                    break;
                                }
                            }
                        } while (cursor.toNextAttribute());
                    }
                    
                    if (!found) {
                        cursor.dispose();
                    } else {
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Customer ID " + customerId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid customer ID format. Use numeric values only.");
            }
        }
        
        if (args.length > 1 && isValidCredit(args[1])) {
            int threshold = Integer.parseInt(args[1]);
            System.out.println("\nCombined safe filtering - Type: " + userType + ", Credit above: " + threshold);
            
            int combinedCount = 0;
            for (XmlObject customer : allCustomers) {
                XmlObject[] typeElements = customer.selectPath("type/text()");
                XmlObject[] creditElements = customer.selectPath("credit/text()");
                
                if (typeElements.length > 0 && creditElements.length > 0) {
                    XmlCursor typeCursor = typeElements[0].newCursor();
                    XmlCursor creditCursor = creditElements[0].newCursor();
                    
                    String type = typeCursor.getTextValue();
                    
                    try {
                        int credit = Integer.parseInt(creditCursor.getTextValue());
                        
                        if (userType.equals(type) && credit > threshold) {
                            XmlObject[] nameElements = customer.selectPath("name/text()");
                            if (nameElements.length > 0) {
                                XmlCursor nameCursor = nameElements[0].newCursor();
                                System.out.println("Customer: " + nameCursor.getTextValue());
                                combinedCount++;
                                nameCursor.dispose();
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Invalid credit format in combined filtering");
                    }
                    
                    typeCursor.dispose();
                    creditCursor.dispose();
                }
            }
            
            System.out.println("Combined results: " + combinedCount);
        }
    }
    
    private static boolean isValidType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_TYPES.contains(type.trim().toLowerCase());
    }
    
    private static boolean isValidCredit(String credit) {
        if (credit == null || credit.trim().isEmpty()) {
            return false;
        }
        return CREDIT_PATTERN.matcher(credit.trim()).matches();
    }
    
    private static boolean isValidCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return false;
        }
        return ID_PATTERN.matcher(customerId.trim()).matches();
    }
}