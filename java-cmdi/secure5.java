import java.io.*;
import java.nio.file.*;

public class secure5 {
    private static final String ALLOWED_BASE_PATH = "/tmp/safe";
    
    public void compressFile(String filename, int level) throws IOException {
        if (filename == null || level < 1 || level > 9) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        
        Path filePath = Paths.get(ALLOWED_BASE_PATH, filename).normalize();
        if (!filePath.startsWith(ALLOWED_BASE_PATH)) {
            throw new SecurityException("Path traversal attempt detected");
        }
        
        String[] command = {"gzip", "-" + level, filePath.toString()};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure5 s = new secure5();
        s.compressFile("test.txt", 9);
    }
}