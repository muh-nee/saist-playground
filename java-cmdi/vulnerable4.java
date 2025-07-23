import java.io.*;

public class vulnerability4 {
    public void systemStatus(String service) throws IOException {
        String command = "systemctl status " + service + "; echo 'Status checked'";
        Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability4 v = new vulnerability4();
        v.systemStatus("apache2; rm -rf /tmp/*; echo 'cleaned'");
    }
}