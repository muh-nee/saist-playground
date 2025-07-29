import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.XPathContext;
import java.io.StringReader;

public class vulnerable4 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<orders>" +
                        "<order id='1'><customer>Alice</customer><total>250.00</total><status>shipped</status></order>" +
                        "<order id='2'><customer>Bob</customer><total>150.00</total><status>pending</status></order>" +
                        "<order id='3'><customer>Charlie</customer><total>300.00</total><status>delivered</status></order>" +
                        "</orders>";
        
        Builder parser = new Builder();
        Document doc = parser.build(new StringReader(xmlData));
        
        String userStatus = args.length > 0 ? args[0] : "shipped' or '1'='1";
        
        String xpathExpr = "//order[status='" + userStatus + "']/customer/text()";
        
        Nodes customers = doc.query(xpathExpr);
        
        System.out.println("XPath Expression: " + xpathExpr);
        System.out.println("Customers found: " + customers.size());
        
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("Customer: " + customers.get(i).getValue());
        }
        
        String numericQuery = "//order[total &gt; " + (args.length > 1 ? args[1] : "0 or 1=1") + "]/customer/text()";
        Nodes numericResults = doc.query(numericQuery);
        
        System.out.println("\nNumeric XPath Query: " + numericQuery);
        System.out.println("Results: " + numericResults.size());
        for (int i = 0; i < numericResults.size(); i++) {
            System.out.println("Customer: " + numericResults.get(i).getValue());
        }
    }
}