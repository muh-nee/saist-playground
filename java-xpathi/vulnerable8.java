import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlCursor;

public class vulnerable8 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<customers>" +
                        "<customer id='1'><name>Alice Corp</name><type>enterprise</type><credit>10000</credit></customer>" +
                        "<customer id='2'><name>Bob LLC</name><type>small</type><credit>5000</credit></customer>" +
                        "<customer id='3'><name>Charlie Inc</name><type>enterprise</type><credit>15000</credit></customer>" +
                        "</customers>";
        
        XmlObject xmlObj = XmlObject.Factory.parse(xmlData);
        
        String userType = args.length > 0 ? args[0] : "enterprise' or '1'='1";
        
        String xpathQuery = "//customer[type='" + userType + "']/name/text()";
        
        XmlObject[] results = xmlObj.selectPath(xpathQuery);
        
        System.out.println("XPath Query: " + xpathQuery);
        System.out.println("Customers found: " + results.length);
        
        for (XmlObject result : results) {
            XmlCursor cursor = result.newCursor();
            System.out.println("Customer: " + cursor.getTextValue());
            cursor.dispose();
        }
        
        String creditQuery = "//customer[credit &gt; " + (args.length > 1 ? args[1] : "0 or 1=1") + "]/name/text()";
        XmlObject[] creditResults = xmlObj.selectPath(creditQuery);
        
        System.out.println("\nCredit XPath Query: " + creditQuery);
        System.out.println("Results: " + creditResults.length);
        
        for (XmlObject result : creditResults) {
            XmlCursor cursor = result.newCursor();
            System.out.println("Customer: " + cursor.getTextValue());
            cursor.dispose();
        }
        
        String attrQuery = "//customer[@id='" + (args.length > 2 ? args[2] : "1' or @id='2") + "']/name/text()";
        XmlObject[] attrResults = xmlObj.selectPath(attrQuery);
        
        System.out.println("\nAttribute XPath Query: " + attrQuery);
        System.out.println("Results: " + attrResults.length);
        
        for (XmlObject result : attrResults) {
            XmlCursor cursor = result.newCursor();
            System.out.println("Customer: " + cursor.getTextValue());
            cursor.dispose();
        }
    }
}