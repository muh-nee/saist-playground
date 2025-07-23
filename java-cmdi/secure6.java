import java.io.*;
import java.util.*;

public class secure6 {
    private static final Set<String> ALLOWED_ENV_VARS = Set.of("PATH", "HOME", "USER");
    
    public void printEnvironment(String envVar) throws IOException {
        if (envVar == null || !ALLOWED_ENV_VARS.contains(envVar)) {
            throw new IllegalArgumentException("Environment variable not allowed");
        }
        
        ProcessBuilder pb = new ProcessBuilder("printenv", envVar);
        pb.environment().clear();
        pb.environment().put("PATH", "/usr/bin:/bin");
        Process process = pb.start();
    }
    
    public static void main(String[] args) throws IOException {
        secure6 s = new secure6();
        s.printEnvironment("PATH");
    }
}