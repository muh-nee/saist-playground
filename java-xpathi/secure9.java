import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class secure9 {
    private static final List&lt;String&gt; ALLOWED_DEPARTMENTS = Arrays.asList(
        "Sales", "Marketing", "Finance", "Engineering", "Support"
    );
    
    private static final List&lt;String&gt; ALLOWED_STATUSES = Arrays.asList(
        "final", "draft", "review", "approved"
    );
    
    private static class Report {
        String id;
        String title;
        String department;
        String status;
        
        Report(String id, String title, String department, String status) {
            this.id = id;
            this.title = title;
            this.department = department;
            this.status = status;
        }
    }
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<reports>" +
                        "<report id='1'><title>Q1 Sales</title><department>Sales</department><status>final</status></report>" +
                        "<report id='2'><title>Q1 Marketing</title><department>Marketing</department><status>draft</status></report>" +
                        "<report id='3'><title>Q1 Finance</title><department>Finance</department><status>final</status></report>" +
                        "</reports>";
        
        String userDept = args.length > 0 ? args[0] : "Sales";
        
        if (!isValidDepartment(userDept)) {
            System.out.println("Error: Invalid department. Allowed departments: " + ALLOWED_DEPARTMENTS);
            return;
        }
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xmlData));
        
        List&lt;Report&gt; reports = parseReportsSafely(reader);
        reader.close();
        
        System.out.println("Department filter: " + userDept);
        System.out.println("Using safe StAX parsing with post-filtering:");
        
        int titleCount = 0;
        for (Report report : reports) {
            if (userDept.equals(report.department)) {
                System.out.println("Title: " + report.title);
                titleCount++;
            }
        }
        
        System.out.println("Titles found: " + titleCount);
        
        if (args.length > 1) {
            String userStatus = args[1];
            
            if (isValidStatus(userStatus)) {
                System.out.println("\nStatus filter: " + userStatus);
                
                int statusCount = 0;
                for (Report report : reports) {
                    if (userStatus.equals(report.status)) {
                        System.out.println("Department: " + report.department);
                        statusCount++;
                    }
                }
                
                System.out.println("Departments with status " + userStatus + ": " + statusCount);
            } else {
                System.out.println("Error: Invalid status. Allowed statuses: " + ALLOWED_STATUSES);
            }
        }
        
        if (args.length > 1 && isValidStatus(args[1])) {
            String userStatus = args[1];
            System.out.println("\nCombined safe filtering - Department: " + userDept + ", Status: " + userStatus);
            
            int combinedCount = 0;
            for (Report report : reports) {
                if (userDept.equals(report.department) && userStatus.equals(report.status)) {
                    System.out.println("Report: " + report.title + " (ID: " + report.id + ")");
                    combinedCount++;
                }
            }
            
            System.out.println("Combined results: " + combinedCount);
        }
        
        if (args.length > 2) {
            String reportId = args[2];
            
            if (isValidReportId(reportId)) {
                System.out.println("\nSafe report ID lookup:");
                
                boolean found = false;
                for (Report report : reports) {
                    if (reportId.equals(report.id)) {
                        System.out.println("Report ID " + reportId + ": " + report.title + 
                                         " (" + report.department + ", " + report.status + ")");
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Report ID " + reportId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid report ID format. Use numeric values only.");
            }
        }
    }
    
    private static List&lt;Report&gt; parseReportsSafely(XMLStreamReader reader) throws XMLStreamException {
        List&lt;Report&gt; reports = new ArrayList&lt;&gt;();
        
        String currentElement = null;
        String reportId = null;
        String title = null;
        String department = null;
        String status = null;
        
        while (reader.hasNext()) {
            int event = reader.next();
            
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentElement = reader.getLocalName();
                    
                    if ("report".equals(currentElement)) {
                        reportId = reader.getAttributeValue(null, "id");
                        title = null;
                        department = null;
                        status = null;
                    }
                    break;
                    
                case XMLStreamConstants.CHARACTERS:
                    if (currentElement != null && reader.getText().trim().length() > 0) {
                        String text = reader.getText().trim();
                        
                        switch (currentElement) {
                            case "title":
                                title = text;
                                break;
                            case "department":
                                department = text;
                                break;
                            case "status":
                                status = text;
                                break;
                        }
                    }
                    break;
                    
                case XMLStreamConstants.END_ELEMENT:
                    if ("report".equals(reader.getLocalName())) {
                        if (reportId != null && title != null && department != null && status != null) {
                            if (isValidDepartment(department) && isValidStatus(status)) {
                                reports.add(new Report(reportId, title, department, status));
                            }
                        }
                    }
                    currentElement = null;
                    break;
            }
        }
        
        return reports;
    }
    
    private static boolean isValidDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_DEPARTMENTS.contains(department.trim());
    }
    
    private static boolean isValidStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_STATUSES.contains(status.trim().toLowerCase());
    }
    
    private static boolean isValidReportId(String reportId) {
        if (reportId == null || reportId.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches("^\\d+$", reportId.trim());
    }
}