import com.ximpleware.*;
import java.io.ByteArrayInputStream;

public class vulnerable7 {
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
        AutoPilot ap = new AutoPilot(vn);
        
        String userAuthor = args.length > 0 ? args[0] : "Smith' or '1'='1";
        
        String xpathExpr = "//book[author='" + userAuthor + "']/title/text()";
        
        ap.selectXPath(xpathExpr);
        
        System.out.println("XPath Expression: " + xpathExpr);
        System.out.println("Titles found:");
        
        int result;
        while ((result = ap.evalXPath()) != -1) {
            System.out.println("Title: " + vn.toString(result));
        }
        
        ap.resetXPath();
        vn.toElement(VTDNav.ROOT);
        
        String priceQuery = "//book[price &gt; " + (args.length > 1 ? args[1] : "0 or 1=1") + "]/author/text()";
        ap.selectXPath(priceQuery);
        
        System.out.println("\nPrice XPath Query: " + priceQuery);
        System.out.println("Authors found:");
        
        while ((result = ap.evalXPath()) != -1) {
            System.out.println("Author: " + vn.toString(result));
        }
    }
}