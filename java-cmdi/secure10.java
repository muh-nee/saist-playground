import java.io.*;
import java.util.logging.*;

public class secure10 {
    private static final Logger logger = Logger.getLogger(secure10.class.getName());
    
    public void logSystemInfo(String component) throws IOException {
        if (component == null || !component.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid component name");
        }
        
        logger.info("Checking system component: " + component);
        
        String[] command = {"uptime"};
        Process process = Runtime.getRuntime().exec(command);
        
        logger.info("System uptime checked for component: " + component);
    }
    
    public static void main(String[] args) throws IOException {
        secure10 s = new secure10();
        s.logSystemInfo("web_server");
    }
}