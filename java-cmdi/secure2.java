import java.io.*;
import java.util.*;

public class secure2 {
    public void listDirectory(String directory) throws IOException {
        if (directory == null || !directory.matches("^[a-zA-Z0-9/_.-]+$")) {
            throw new IllegalArgumentException("Invalid directory path");
        }
        
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("ls", "-la", directory);
        Process process = pb.start();
    }
    
    public static void main(String[] args) throws IOException {
        secure2 s = new secure2();
        s.listDirectory("/tmp");
    }
}