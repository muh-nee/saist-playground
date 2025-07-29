import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class secure2 {
    private static final List&lt;String&gt; ALLOWED_DEPARTMENTS = Arrays.asList(
        "IT", "HR", "Finance", "Marketing", "Sales"
    );
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<employees>" +
                        "<employee id='1'><name>John</name><salary>50000</salary><department>IT</department></employee>" +
                        "<employee id='2'><name>Jane</name><salary>60000</salary><department>HR</department></employee>" +
                        "<employee id='3'><name>Bob</name><salary>70000</salary><department>Finance</department></employee>" +
                        "</employees>";
        
        Document document = DocumentHelper.parseText(xmlData);
        
        String userDept = args.length > 0 ? args[0] : "IT";
        
        if (!isValidDepartment(userDept)) {
            System.out.println("Error: Invalid department. Allowed departments: " + ALLOWED_DEPARTMENTS);
            return;
        }
        
        Map&lt;String, String&gt; variables = new HashMap&lt;&gt;();
        variables.put("dept", userDept);
        
        String xpathExpr = "
        
        XPath xpath = document.createXPath(xpathExpr);
        xpath.setVariableContext(new SimpleVariableContext(variables));
        
        List&lt;Node&gt; salaries = xpath.selectNodes(document);
        
        System.out.println("Department filter: " + userDept);
        System.out.println("XPath Expression: " + xpathExpr);
        System.out.println("Salaries found: " + salaries.size());
        
        for (Node salary : salaries) {
            System.out.println("Salary: " + salary.getText());
        }
        
        String nameXpathExpr = "
        XPath nameXpath = document.createXPath(nameXpathExpr);
        nameXpath.setVariableContext(new SimpleVariableContext(variables));
        
        List&lt;Node&gt; names = nameXpath.selectNodes(document);
        
        System.out.println("\nNames found: " + names.size());
        for (Node name : names) {
            System.out.println("Name: " + name.getText());
        }
    }
    
    private static boolean isValidDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_DEPARTMENTS.contains(department.trim());
    }
    
    private static class SimpleVariableContext implements org.dom4j.VariableContext {
        private final Map&lt;String, String&gt; variables;
        
        public SimpleVariableContext(Map&lt;String, String&gt; variables) {
            this.variables = variables;
        }
        
        @Override
        public Object getVariableValue(String namespaceURI, String prefix, String localName) {
            return variables.get(localName);
        }
    }
}