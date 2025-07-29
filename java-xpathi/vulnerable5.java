import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class vulnerable5 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<students>" +
                        "<student id='1'><name>Alex</name><grade>A</grade><subject>Math</subject></student>" +
                        "<student id='2'><name>Beth</name><grade>B</grade><subject>Science</subject></student>" +
                        "<student id='3'><name>Carl</name><grade>A</grade><subject>Math</subject></student>" +
                        "</students>";
        
        Processor processor = new Processor(false);
        DocumentBuilder builder = processor.newDocumentBuilder();
        XdmNode document = builder.build(new StreamSource(new StringReader(xmlData)));
        
        String userSubject = args.length > 0 ? args[0] : "Math' or '1'='1";
        
        XPathCompiler xpathCompiler = processor.newXPathCompiler();
        
        String xpathExpr = "//student[subject='" + userSubject + "']/name/text()";
        
        XPathSelector selector = xpathCompiler.compile(xpathExpr).load();
        selector.setContextItem(document);
        
        System.out.println("XPath Expression: " + xpathExpr);
        
        XdmValue result = selector.evaluate();
        System.out.println("Students found: " + result.size());
        
        for (XdmItem item : result) {
            System.out.println("Student: " + item.getStringValue());
        }
        
        String funcQuery = "//student[grade='" + (args.length > 1 ? args[1] : "A' or starts-with(name,'") + "']/name/text()";
        XPathSelector funcSelector = xpathCompiler.compile(funcQuery).load();
        funcSelector.setContextItem(document);
        
        XdmValue funcResult = funcSelector.evaluate();
        System.out.println("\nFunction XPath Query: " + funcQuery);
        System.out.println("Results: " + funcResult.size());
        
        for (XdmItem item : funcResult) {
            System.out.println("Student: " + item.getStringValue());
        }
    }
}