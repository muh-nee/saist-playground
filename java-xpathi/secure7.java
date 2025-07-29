import com.ximpleware.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class secure7 {
    private static final List&lt;String&gt; ALLOWED_AUTHORS = Arrays.asList(
        "Smith", "Jones", "Brown", "Wilson", "Davis"
    );
    
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    
    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<books>" +
                        "<book id='1'><title>Java Security</title><author>Smith</author><price>45.00</price></book>" +
                        "<book id='2'><title>XML Processing</title><author>Jones</author><price>35.00</price></book>" +
                        "<book id='3'><title>Web Development</title><author>Brown</author><price>50.00</price></book>" +
                        "</books>";
        
        VTDGen vg = new VTDGen();
        vg.setDoc(xmlData.getBytes());
        vg.parse(true);
        
        VTDNav vn = vg.getNav();
        
        String userAuthor = args.length > 0 ? args[0] : "Smith";
        
        if (!isValidAuthor(userAuthor)) {
            System.out.println("Error: Invalid author. Allowed authors: " + ALLOWED_AUTHORS);
            return;
        }
        
        System.out.println("Author filter: " + userAuthor);
        System.out.println("Using safe VTD navigation:");
        
        int titleCount = 0;
        if (vn.toElement(VTDNav.ROOT)) {
            if (vn.toElement(VTDNav.FIRST_CHILD, "books")) {
                if (vn.toElement(VTDNav.FIRST_CHILD, "book")) {
                    do {
                        if (vn.toElement(VTDNav.FIRST_CHILD, "author")) {
                            int authorIndex = vn.getText();
                            if (authorIndex != -1) {
                                String author = vn.toString(authorIndex);
                                
                                if (userAuthor.equals(author)) {
                                    vn.toElement(VTDNav.PARENT);
                                    if (vn.toElement(VTDNav.FIRST_CHILD, "title")) {
                                        int titleIndex = vn.getText();
                                        if (titleIndex != -1) {
                                            System.out.println("Title: " + vn.toString(titleIndex));
                                            titleCount++;
                                        }
                                        vn.toElement(VTDNav.PARENT);
                                    }
                                } else {
                                    vn.toElement(VTDNav.PARENT);
                                }
                            }
                        }
                    } while (vn.toElement(VTDNav.NEXT_SIBLING, "book"));
                }
            }
        }
        
        System.out.println("Titles found: " + titleCount);
        
        if (args.length > 1) {
            String priceThreshold = args[1];
            
            if (isValidPrice(priceThreshold)) {
                double threshold = Double.parseDouble(priceThreshold);
                System.out.println("\nSafe price filtering above: " + threshold);
                
                vn.toElement(VTDNav.ROOT);
                
                int priceCount = 0;
                if (vn.toElement(VTDNav.FIRST_CHILD, "books")) {
                    if (vn.toElement(VTDNav.FIRST_CHILD, "book")) {
                        do {
                            if (vn.toElement(VTDNav.FIRST_CHILD, "price")) {
                                int priceIndex = vn.getText();
                                if (priceIndex != -1) {
                                    try {
                                        double price = Double.parseDouble(vn.toString(priceIndex));
                                        
                                        if (price > threshold) {
                                            vn.toElement(VTDNav.PARENT);
                                            if (vn.toElement(VTDNav.FIRST_CHILD, "author")) {
                                                int authorIndex = vn.getText();
                                                if (authorIndex != -1) {
                                                    System.out.println("Author: " + vn.toString(authorIndex));
                                                    priceCount++;
                                                }
                                                vn.toElement(VTDNav.PARENT);
                                            }
                                        } else {
                                            vn.toElement(VTDNav.PARENT);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Warning: Invalid price format in book");
                                        vn.toElement(VTDNav.PARENT);
                                    }
                                }
                            }
                        } while (vn.toElement(VTDNav.NEXT_SIBLING, "book"));
                    }
                }
                
                System.out.println("Authors with books above price threshold: " + priceCount);
            } else {
                System.out.println("Error: Invalid price format. Use numeric values with up to 2 decimal places.");
            }
        }
        
        if (args.length > 2) {
            String bookId = args[2];
            
            if (isValidBookId(bookId)) {
                System.out.println("\nSafe book ID lookup:");
                
                vn.toElement(VTDNav.ROOT);
                
                boolean found = false;
                if (vn.toElement(VTDNav.FIRST_CHILD, "books")) {
                    if (vn.toElement(VTDNav.FIRST_CHILD, "book")) {
                        do {
                            int idIndex = vn.getAttrVal("id");
                            if (idIndex != -1) {
                                String id = vn.toString(idIndex);
                                
                                if (bookId.equals(id)) {
                                    if (vn.toElement(VTDNav.FIRST_CHILD, "title")) {
                                        int titleIndex = vn.getText();
                                        if (titleIndex != -1) {
                                            System.out.println("Book ID " + bookId + ": " + vn.toString(titleIndex));
                                            found = true;
                                        }
                                        vn.toElement(VTDNav.PARENT);
                                    }
                                    break;
                                }
                            }
                        } while (vn.toElement(VTDNav.NEXT_SIBLING, "book"));
                    }
                }
                
                if (!found) {
                    System.out.println("Book ID " + bookId + " not found.");
                }
            } else {
                System.out.println("Error: Invalid book ID format. Use numeric values only.");
            }
        }
    }
    
    private static boolean isValidAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return false;
        }
        return ALLOWED_AUTHORS.contains(author.trim());
    }
    
    private static boolean isValidPrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            return false;
        }
        return PRICE_PATTERN.matcher(price.trim()).matches();
    }
    
    private static boolean isValidBookId(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches("^\\d+$", bookId.trim());
    }
}