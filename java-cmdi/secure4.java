import java.io.*;
import java.util.*;

public class secure4 {
    private static final Set<String> ALLOWED_SERVICES = Set.of("apache2", "nginx", "mysql", "postgresql");
    
    public void checkServiceStatus(String service) throws IOException {
        if (service == null || !ALLOWED_SERVICES.contains(service)) {
            throw new IllegalArgumentException("Service not allowed");
        }
        
        String sanitizedService = service.replaceAll("[^a-zA-Z0-9]", "");
        String[] command = {"systemctl", "status", sanitizedService};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure4 s = new secure4();
        s.checkServiceStatus("apache2");
    }
}