import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import javax.xml.stream.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class vulnerable10 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<conferences>" +
                        "<conference id='1'><name>JavaOne</name><location>San Francisco</location><year>2023</year></conference>" +
                        "<conference id='2'><name>Devoxx</name><location>Belgium</location><year>2023</year></conference>" +
                        "<conference id='3'><name>SpringOne</name><location>Austin</location><year>2024</year></conference>" +
                        "</conferences>";
        
        WstxInputFactory inputFactory = new WstxInputFactory();
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(xmlData));
        
        System.out.println("Woodstox parsing (for demonstration):");
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if ("name".equals(reader.getLocalName())) {
                    System.out.println("Found conference: " + reader.getElementText());
                }
            }
        }
        reader.close();
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
        
        String userLocation = args.length > 0 ? args[0] : "San Francisco' or '1'='1";
        
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        
        String xpathQuery = "//conference[location='" + userLocation + "']/name/text()";
        
        XPathExpression expr = xpath.compile(xpathQuery);
        NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("\nXPath Query: " + xpathQuery);
        System.out.println("Conferences found: " + result.getLength());
        
        for (int i = 0; i < result.getLength(); i++) {
            System.out.println("Conference: " + result.item(i).getNodeValue());
        }
        
        String yearQuery = "//conference[year=" + (args.length > 1 ? args[1] : "2023 or year=2024") + "]/location/text()";
        XPathExpression yearExpr = xpath.compile(yearQuery);
        NodeList yearResult = (NodeList) yearExpr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("\nYear XPath Query: " + yearQuery);
        System.out.println("Locations found: " + yearResult.getLength());
        
        for (int i = 0; i < yearResult.getLength(); i++) {
            System.out.println("Location: " + yearResult.item(i).getNodeValue());
        }
        
        // Demonstrate count() function injection vulnerability
        String countQuery = "count(//conference[location='" + (args.length > 2 ? args[2] : "' or '1'='1") + "'])";
        Double count = (Double) xpath.evaluate(countQuery, doc, XPathConstants.NUMBER);
        
        System.out.println("\nCount XPath Query: " + countQuery);
        System.out.println("Count result: " + count.intValue());
    }
}