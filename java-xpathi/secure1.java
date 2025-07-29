import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.StringReader;
import org.xml.sax.InputSource;
import java.util.regex.Pattern;

public class secure1 {
    private static final Pattern SAFE_USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<users>" +
                        "<user id='1'><name>admin</name><password>secret123</password></user>" +
                        "<user id='2'><name>guest</name><password>guest456</password></user>" +
                        "</users>";
        
        String userInput = args.length > 0 ? args[0] : "admin";
        
        if (!isValidUsername(userInput)) {
            System.out.println("Error: Invalid username format. Only alphanumeric characters allowed.");
            return;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
        
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        
        String xpathQuery = "//user[name='" + sanitizeForXPath(userInput) + "']/password/text()";
        
        XPathExpression expr = xpath.compile(xpathQuery);
        NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("Searching for user: " + userInput);
        System.out.println("XPath Query: " + xpathQuery);
        System.out.println("Results found: " + result.getLength());
        
        for (int i = 0; i < result.getLength(); i++) {
            System.out.println("Password: " + result.item(i).getNodeValue());
        }
    }
    
    private static boolean isValidUsername(String username) {
        if (username == null || username.length() > 50) {
            return false;
        }
        return SAFE_USERNAME_PATTERN.matcher(username).matches();
    }
    
    private static String sanitizeForXPath(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("'", "''");
    }
}