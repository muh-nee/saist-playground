import java.io.*;
import java.nio.file.*;

public class secure7 {
    private static final String BACKUP_DIR = "/tmp/backup";
    
    public void copyFile(String filename) throws IOException {
        if (filename == null || !filename.matches("^[a-zA-Z0-9._-]+$")) {
            throw new IllegalArgumentException("Invalid filename");
        }
        
        Path sourcePath = Paths.get("/home/user", filename).normalize();
        Path destPath = Paths.get(BACKUP_DIR, filename).normalize();
        
        if (!sourcePath.startsWith("/home/user") || !destPath.startsWith(BACKUP_DIR)) {
            throw new SecurityException("Invalid path");
        }
        
        String[] command = {"cp", sourcePath.toString(), destPath.toString()};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure7 s = new secure7();
        s.copyFile("document.txt");
    }
}