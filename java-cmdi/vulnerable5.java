import java.io.*;

public class vulnerability5 {
    public void compressFile(String filename, String level) throws IOException {
        String[] command = {"gzip", "-" + level, filename};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability5 v = new vulnerability5();
        v.compressFile("test.txt", "9 /etc/passwd");
    }
}