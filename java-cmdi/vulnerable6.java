import java.io.*;
import java.util.*;

public class vulnerability6 {
    public void setEnvironmentAndRun(String envVar, String value) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("printenv");
        Map<String, String> env = pb.environment();
        env.put(envVar, value);
        pb.command("sh", "-c", "echo $" + envVar + " && date");
        Process process = pb.start();
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability6 v = new vulnerability6();
        v.setEnvironmentAndRun("TEST", "value; rm /tmp/test");
    }
}