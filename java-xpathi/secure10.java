import com.ctc.wstx.stax.WstxInputFactory;
import javax.xml.stream.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class secure10 {
    private static final List&lt;String&gt; ALLOWED_LOCATIONS = Arrays.asList(
        "San Francisco", "Belgium", "Austin", "New York", "London"
    );
    
    private static final Pattern YEAR_PATTERN = Pattern.compile("^20\\d{2}$");
    
    private static class Conference {
        String id;
        String name;
        String location;
        String year;
        
        Conference(String id, String name, String location, String year) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.year = year;
        }
    }
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<conferences>" +
                        "<conference id='1'><name>JavaOne</name><location>San Francisco</location><year>2023</year></conference>" +
                        "<conference id='2'><name>Devoxx</name><location>Belgium</location><year>2023</year></conference>" +
                        "<conference id='3'><name>SpringOne</name><location>Austin</location><year>2024</year></conference>" +
                        "</conferences>";
        
        String userLocation = args.length > 0 ? args[0] : "San Francisco";
        
        if (!isValidLocation(userLocation)) {
            System.out.println("Error: Invalid location. Allowed locations: " + ALLOWED_LOCATIONS);
            return;
        }
        
        WstxInputFactory inputFactory = new WstxInputFactory();
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(xmlData));
        
        List&lt;Conference&gt; conferences = parseConferencesSafely(reader);
        reader.close();
        
        System.out.println("Location filter: " + userLocation);
        System.out.println("Using safe Woodstox parsing with post-filtering:");
        
        int conferenceCount = 0;
        for (Conference conf : conferences) {
            if (userLocation.equals(conf.location)) {
                System.out.println("Conference: " + conf.name);
                conferenceCount++;
            }
        }
        
        System.out.println("Conferences found: " + conferenceCount);
        
        if (args.length > 1) {
            String userYear = args[1];
            
            if (isValidYear(userYear)) {
                System.out.println("\nYear filter: " + userYear);
                
                int yearCount = 0;
                for (Conference conf : conferences) {
                    if (userYear.equals(conf.year)) {
                        System.out.println("Location: " + conf.location);
                        yearCount++;
                    }
                }
                
                System.out.println("Locations with conferences in " + userYear + ": " + yearCount);
            } else {
                System.out.println("Error: Invalid year format. Use 4-digit year (20XX).");
            }
        }
        
        if (args.length > 1 && isValidYear(args[1])) {
            String userYear = args[1];
            System.out.println("\nCombined safe filtering - Location: " + userLocation + ", Year: " + userYear);
            
            int combinedCount = 0;
            for (Conference conf : conferences) {
                if (userLocation.equals(conf.location) && userYear.equals(conf.year)) {
                    System.out.println("Conference: " + conf.name + " (ID: " + conf.id + ")");
                    combinedCount++;
                }
            }
            
            System.out.println("Combined results: " + combinedCount);
        }
        
        if (args.length > 2) {
            String confId = args[2];
            
            if (isValidConferenceId(confId)) {
                System.out.println("\nSafe conference ID lookup:");
                
                boolean found = false;
                for (Conference conf : conferences) {
                    if (confId.equals(conf.id)) {
                        System.out.println("Conference ID " + confId + ": " + conf.name + 
                                         " (" + conf.location + ", " + conf.year + ")");
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Conference ID " + confId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid conference ID format. Use numeric values only.");
            }
        }
        
        System.out.println("\nSafe count operations:");
        
        int totalConferences = conferences.size();
        System.out.println("Total conferences: " + totalConferences);
        
        int locationCount = 0;
        for (Conference conf : conferences) {
            if (userLocation.equals(conf.location)) {
                locationCount++;
            }
        }
        System.out.println("Conferences in " + userLocation + ": " + locationCount);
        
        if (args.length > 1 && isValidYear(args[1])) {
            String userYear = args[1];
            int yearLocationCount = 0;
            
            for (Conference conf : conferences) {
                if (userLocation.equals(conf.location) && userYear.equals(conf.year)) {
                    yearLocationCount++;
                }
            }
            
            System.out.println("Conferences in " + userLocation + " during " + userYear + ": " + yearLocationCount);
        }
    }
    
    private static List&lt;Conference&gt; parseConferencesSafely(XMLStreamReader reader) throws XMLStreamException {
        List&lt;Conference&gt; conferences = new ArrayList&lt;&gt;();
        
        String currentElement = null;
        String confId = null;
        String name = null;
        String location = null;
        String year = null;
        
        while (reader.hasNext()) {
            int event = reader.next();
            
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentElement = reader.getLocalName();
                    
                    if ("conference".equals(currentElement)) {
                        confId = reader.getAttributeValue(null, "id");
                        name = null;
                        location = null;
                        year = null;
                    }
                    break;
                    
                case XMLStreamConstants.CHARACTERS:
                    if (currentElement != null && reader.getText().trim().length() > 0) {
                        String text = reader.getText().trim();
                        
                        switch (currentElement) {
                            case "name":
                                name = text;
                                break;
                            case "location":
                                location = text;
                                break;
                            case "year":
                                year = text;
                                break;
                        }
                    }
                    break;
                    
                case XMLStreamConstants.END_ELEMENT:
                    if ("conference".equals(reader.getLocalName())) {
                        if (confId != null && name != null && location != null && year != null) {
                            if (isValidLocation(location) && isValidYear(year)) {
                                conferences.add(new Conference(confId, name, location, year));
                            }
                        }
                    }
                    currentElement = null;
                    break;
            }
        }
        
        return conferences;
    }
    
    private static boolean isValidLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_LOCATIONS.contains(location.trim());
    }
    
    private static boolean isValidYear(String year) {
        if (year == null || year.trim().isEmpty()) {
            return false;
        }
        return YEAR_PATTERN.matcher(year.trim()).matches();
    }
    
    private static boolean isValidConferenceId(String confId) {
        if (confId == null || confId.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches("^\\d+$", confId.trim());
    }
}