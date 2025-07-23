import java.io.*;
import java.nio.file.*;

public class secure9 {
    public void processData(String data) throws IOException {
        if (data == null || !data.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new IllegalArgumentException("Invalid data format");
        }
        
        Path tempFile = Files.createTempFile("secure_process", ".txt");
        Files.write(tempFile, data.getBytes());
        
        String[] command = {"wc", "-l", tempFile.toString()};
        Process process = Runtime.getRuntime().exec(command);
        
        Files.deleteIfExists(tempFile);
    }
    
    public static void main(String[] args) throws IOException {
        secure9 s = new secure9();
        s.processData("This is safe data to process");
    }
}