import java.io.*;

public class vulnerability1 {
    public void executeCommand(String userInput) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String command = "ping -c 1 " + userInput;
        Process process = runtime.exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability1 v = new vulnerability1();
        v.executeCommand("example.com; cat /etc/passwd");
    }
}