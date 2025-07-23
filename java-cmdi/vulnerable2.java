import java.io.*;
import java.util.*;

public class vulnerability2 {
    public void executeCommand(String userInput) throws IOException {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("sh", "-c", "ls " + userInput);
        Process process = pb.start();
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability2 v = new vulnerability2();
        v.executeCommand("/tmp && whoami");
    }
}