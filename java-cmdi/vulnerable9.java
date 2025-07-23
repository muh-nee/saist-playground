import java.io.*;
import java.nio.file.*;

public class vulnerability9 {
    public void processData(String userInput) throws IOException {
        Path tempFile = Files.createTempFile("process", ".sh");
        String script = "#!/bin/bash\necho 'Processing: " + userInput + "'";
        Files.write(tempFile, script.getBytes());
        
        Runtime.getRuntime().exec(new String[]{"chmod", "+x", tempFile.toString()});
        Runtime.getRuntime().exec(tempFile.toString());
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability9 v = new vulnerability9();
        v.processData("data'\nrm -rf /tmp/*\necho 'cleaned");
    }
}