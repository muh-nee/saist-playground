import java.io.*;

public class secure1 {
    public void executeCommand(String host) throws IOException {
        if (host == null || !host.matches("^[a-zA-Z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid host format");
        }
        
        String[] command = {"ping", "-c", "1", host};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure1 s = new secure1();
        s.executeCommand("example.com");
    }
}