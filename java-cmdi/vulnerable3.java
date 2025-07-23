import java.io.*;

public class vulnerability3 {
    public void searchFiles(String pattern) throws IOException {
        String command = "find /tmp -name \"" + pattern + "\"";
        Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws IOException {
        vulnerability3 v = new vulnerability3();
        v.searchFiles("*.txt\" -exec rm {} \\; -o -name \"*");
    }
}