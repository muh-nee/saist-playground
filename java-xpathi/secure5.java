import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class secure5 {
    private static final List&lt;String&gt; ALLOWED_SUBJECTS = Arrays.asList(
        "Math", "Science", "English", "History", "Art"
    );
    
    private static final List&lt;String&gt; ALLOWED_GRADES = Arrays.asList(
        "A", "B", "C", "D", "F"
    );
    
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
        
        String userSubject = args.length > 0 ? args[0] : "Math";
        
        if (!isValidSubject(userSubject)) {
            System.out.println("Error: Invalid subject. Allowed subjects: " + ALLOWED_SUBJECTS);
            return;
        }
        
        XPathCompiler xpathCompiler = processor.newXPathCompiler();
        
        xpathCompiler.declareVariable(new QName("subject"));
        
        String xpathExpr = "
        
        XPathSelector selector = xpathCompiler.compile(xpathExpr).load();
        selector.setContextItem(document);
        
        selector.setVariable(new QName("subject"), new XdmAtomicValue(userSubject));
        
        System.out.println("Subject filter: " + userSubject);
        System.out.println("XPath Expression with variable: " + xpathExpr);
        
        XdmValue result = selector.evaluate();
        System.out.println("Students found: " + result.size());
        
        for (XdmItem item : result) {
            System.out.println("Student: " + item.getStringValue());
        }
        
        if (args.length > 1) {
            String userGrade = args[1];
            
            if (isValidGrade(userGrade)) {
                xpathCompiler.declareVariable(new QName("grade"));
                
                String gradeXpathExpr = "
                XPathSelector gradeSelector = xpathCompiler.compile(gradeXpathExpr).load();
                gradeSelector.setContextItem(document);
                gradeSelector.setVariable(new QName("grade"), new XdmAtomicValue(userGrade));
                
                XdmValue gradeResult = gradeSelector.evaluate();
                System.out.println("\nGrade filter: " + userGrade);
                System.out.println("XPath Expression: " + gradeXpathExpr);
                System.out.println("Students with grade " + userGrade + ": " + gradeResult.size());
                
                for (XdmItem item : gradeResult) {
                    System.out.println("Student: " + item.getStringValue());
                }
            } else {
                System.out.println("Error: Invalid grade. Allowed grades: " + ALLOWED_GRADES);
            }
        }
        
        if (args.length > 1 && isValidGrade(args[1])) {
            xpathCompiler.declareVariable(new QName("subjectVar"));
            xpathCompiler.declareVariable(new QName("gradeVar"));
            
            String combinedXpathExpr = "
            XPathSelector combinedSelector = xpathCompiler.compile(combinedXpathExpr).load();
            combinedSelector.setContextItem(document);
            combinedSelector.setVariable(new QName("subjectVar"), new XdmAtomicValue(userSubject));
            combinedSelector.setVariable(new QName("gradeVar"), new XdmAtomicValue(args[1]));
            
            XdmValue combinedResult = combinedSelector.evaluate();
            System.out.println("\nCombined filter - Subject: " + userSubject + ", Grade: " + args[1]);
            System.out.println("XPath Expression: " + combinedXpathExpr);
            System.out.println("Students found: " + combinedResult.size());
            
            for (XdmItem item : combinedResult) {
                System.out.println("Student: " + item.getStringValue());
            }
        }
    }
    
    private static boolean isValidSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_SUBJECTS.contains(subject.trim());
    }
    
    private static boolean isValidGrade(String grade) {
        if (grade == null || grade.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_GRADES.contains(grade.trim().toUpperCase());
    }
}