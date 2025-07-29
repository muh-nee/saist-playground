import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.filter.Filters;
import java.io.StringReader;
import java.util.List;

public class vulnerable3 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<products>" +
                        "<product id='1'><name>Laptop</name><price>1000</price><category>Electronics</category></product>" +
                        "<product id='2'><name>Book</name><price>20</price><category>Education</category></product>" +
                        "<product id='3'><name>Phone</name><price>800</price><category>Electronics</category></product>" +
                        "</products>";
        
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xmlData));
        
        String userCategory = args.length > 0 ? args[0] : "Electronics' or '1'='1";
        
        XPathFactory xFactory = XPathFactory.instance();
        
        String xpathQuery = "//product[category='" + userCategory + "']/price";
        
        XPathExpression&lt;Element&gt; expr = xFactory.compile(xpathQuery, Filters.element());
        List&lt;Element&gt; prices = expr.evaluate(document);
        
        System.out.println("XPath Query: " + xpathQuery);
        System.out.println("Prices found: " + prices.size());
        
        for (Element price : prices) {
            System.out.println("Price: " + price.getText());
        }
        
        String booleanQuery = "//product[category='" + userCategory + "' and price &gt; 0]/name";
        XPathExpression&lt;Element&gt; boolExpr = xFactory.compile(booleanQuery, Filters.element());
        List&lt;Element&gt; names = boolExpr.evaluate(document);
        
        System.out.println("\nBoolean XPath Query: " + booleanQuery);
        System.out.println("Names found: " + names.size());
        for (Element name : names) {
            System.out.println("Product: " + name.getText());
        }
    }
}