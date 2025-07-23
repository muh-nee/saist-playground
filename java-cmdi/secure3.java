import java.io.*;
import java.util.*;

public class secure3 {
    private static final Set<String> ALLOWED_PATTERNS = Set.of("*.txt", "*.log", "*.dat");
    
    public void searchFiles(String pattern) throws IOException {
        if (pattern == null || !ALLOWED_PATTERNS.contains(pattern)) {
            throw new IllegalArgumentException("Pattern not in whitelist");
        }
        
        String[] command = {"find", "/tmp", "-name", pattern, "-type", "f"};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure3 s = new secure3();
        s.searchFiles("*.txt");
    }
}