import java.io.*;
import java.util.*;

public class secure8 {
    private static final Set<String> ALLOWED_SCRIPTS = Set.of("data_processor", "log_analyzer", "backup_tool");
    
    public void runScript(String scriptName, String inputFile) throws IOException {
        if (scriptName == null || !ALLOWED_SCRIPTS.contains(scriptName)) {
            throw new IllegalArgumentException("Script not in whitelist");
        }
        
        if (inputFile == null || !inputFile.matches("^[a-zA-Z0-9._-]+\\.txt$")) {
            throw new IllegalArgumentException("Invalid input file format");
        }
        
        String[] command = {"python", "/opt/scripts/" + scriptName + ".py", inputFile};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        secure8 s = new secure8();
        s.runScript("data_processor", "input.txt");
    }
}