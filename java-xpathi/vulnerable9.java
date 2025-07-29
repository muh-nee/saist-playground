import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class vulnerable9 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<reports>" +
                        "<report id='1'><title>Q1 Sales</title><department>Sales</department><status>final</status></report>" +
                        "<report id='2'><title>Q1 Marketing</title><department>Marketing</department><status>draft</status></report>" +
                        "<report id='3'><title>Q1 Finance</title><department>Finance</department><status>final</status></report>" +
                        "</reports>";
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xmlData));
        
        System.out.println("StAX parsing (for demonstration):");
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if ("title".equals(reader.getLocalName())) {
                    System.out.println("Found title: " + reader.getElementText());
                }
            }
        }
        reader.close();
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
        
        String userDept = args.length > 0 ? args[0] : "Sales' or '1'='1";
        
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        
        String xpathQuery = "//report[department='" + userDept + "']/title/text()";
        
        XPathExpression expr = xpath.compile(xpathQuery);
        NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("\nXPath Query: " + xpathQuery);
        System.out.println("Titles found: " + result.getLength());
        
        for (int i = 0; i < result.getLength(); i++) {
            System.out.println("Title: " + result.item(i).getNodeValue());
        }
        
        String statusQuery = "//report[status='" + (args.length > 1 ? args[1] : "final' or status='draft") + "']/department/text()";
        XPathExpression statusExpr = xpath.compile(statusQuery);
        NodeList statusResult = (NodeList) statusExpr.evaluate(doc, XPathConstants.NODESET);
        
        System.out.println("\nStatus XPath Query: " + statusQuery);
        System.out.println("Departments found: " + statusResult.getLength());
        
        for (int i = 0; i < statusResult.getLength(); i++) {
            System.out.println("Department: " + statusResult.item(i).getNodeValue());
        }
    }
}