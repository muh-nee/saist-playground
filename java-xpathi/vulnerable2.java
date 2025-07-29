import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import java.util.List;

public class vulnerable2 {
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<employees>" +
                        "<employee id='1'><name>John</name><salary>50000</salary><department>IT</department></employee>" +
                        "<employee id='2'><name>Jane</name><salary>60000</salary><department>HR</department></employee>" +
                        "<employee id='3'><name>Bob</name><salary>70000</salary><department>Finance</department></employee>" +
                        "</employees>";
        
        Document document = DocumentHelper.parseText(xmlData);
        
        String userDept = args.length > 0 ? args[0] : "IT' or '1'='1";
        
        String xpathExpr = "//employee[department='" + userDept + "']/salary/text()";
        
        XPath xpath = document.createXPath(xpathExpr);
        List&lt;Node&gt; salaries = xpath.selectNodes(document);
        
        System.out.println("XPath Expression: " + xpathExpr);
        System.out.println("Salaries found: " + salaries.size());
        
        for (Node salary : salaries) {
            System.out.println("Salary: " + salary.getText());
        }
        
        String xpathFunc = "//employee[department='" + userDept + "']/name/text()";
        XPath nameXpath = document.createXPath(xpathFunc);
        List&lt;Node&gt; names = nameXpath.selectNodes(document);
        
        System.out.println("\nNames found: " + names.size());
        for (Node name : names) {
            System.out.println("Name: " + name.getText());
        }
    }
}