import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.filter.Filters;
import java.io.StringReader;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

public class secure3 {
    private static final List&lt;String&gt; ALLOWED_CATEGORIES = Arrays.asList(
        "Electronics", "Education", "Clothing", "Books", "Home"
    );
    
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<products>" +
                        "<product id='1'><name>Laptop</name><price>1000</price><category>Electronics</category></product>" +
                        "<product id='2'><name>Book</name><price>20</price><category>Education</category></product>" +
                        "<product id='3'><name>Phone</name><price>800</price><category>Electronics</category></product>" +
                        "</products>";
        
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xmlData));
        
        String userCategory = args.length > 0 ? args[0] : "Electronics";
        
        if (!isValidCategory(userCategory)) {
            System.out.println("Error: Invalid category. Allowed categories: " + ALLOWED_CATEGORIES);
            return;
        }
        
        XPathFactory xFactory = XPathFactory.instance();
        
        List&lt;Element&gt; allProducts = document.getRootElement().getChildren("product");
        
        System.out.println("Category filter: " + userCategory);
        System.out.println("Products found with safe iteration:");
        
        int priceCount = 0;
        for (Element product : allProducts) {
            Element categoryElement = product.getChild("category");
            if (categoryElement != null && userCategory.equals(categoryElement.getText())) {
                Element priceElement = product.getChild("price");
                if (priceElement != null) {
                    System.out.println("Price: " + priceElement.getText());
                    priceCount++;
                }
            }
        }
        
        System.out.println("Total prices found: " + priceCount);
        
        String safeXpathQuery = "
        XPathExpression&lt;Element&gt; expr = xFactory.compile(safeXpathQuery, Filters.element());
        List&lt;Element&gt; allPrices = expr.evaluate(document);
        
        System.out.println("\nUsing safe XPath with post-filtering:");
        System.out.println("Safe XPath Query: " + safeXpathQuery);
        
        int filteredCount = 0;
        for (Element price : allPrices) {
            Element product = price.getParentElement();
            Element categoryElement = product.getChild("category");
            if (categoryElement != null && userCategory.equals(categoryElement.getText())) {
                System.out.println("Filtered Price: " + price.getText());
                filteredCount++;
            }
        }
        
        System.out.println("Filtered results: " + filteredCount);
        
        if (args.length > 1) {
            String priceThreshold = args[1];
            if (isValidPrice(priceThreshold)) {
                double threshold = Double.parseDouble(priceThreshold);
                System.out.println("\nSafe price filtering above: " + threshold);
                
                for (Element product : allProducts) {
                    Element priceElement = product.getChild("price");
                    Element categoryElement = product.getChild("category");
                    
                    if (priceElement != null && categoryElement != null && 
                        userCategory.equals(categoryElement.getText())) {
                        
                        double price = Double.parseDouble(priceElement.getText());
                        if (price > threshold) {
                            Element nameElement = product.getChild("name");
                            System.out.println("Product: " + (nameElement != null ? nameElement.getText() : "Unknown"));
                        }
                    }
                }
            } else {
                System.out.println("Error: Invalid price format. Use numeric values only.");
            }
        }
    }
    
    private static boolean isValidCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_CATEGORIES.contains(category.trim());
    }
    
    private static boolean isValidPrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            return false;
        }
        return NUMERIC_PATTERN.matcher(price.trim()).matches();
    }
}