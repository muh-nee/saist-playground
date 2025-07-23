import java.io.*;
import java.util.logging.*;

public class vulnerability10 {
    private static final Logger logger = Logger.getLogger(vulnerability10.class.getName());
    
    public void debugSystem(String debugCmd) throws IOException {
        logger.info("Running debug command: " + debugCmd);
        String command = "bash -c 'echo Debug: && " + debugCmd + "'";
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability10 v = new vulnerability10();
        v.debugSystem("ps aux' && cat /etc/shadow && echo 'done");
    }
}