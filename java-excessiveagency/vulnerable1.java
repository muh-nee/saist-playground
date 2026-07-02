package main;

import dev.langchain4j.agent.tool.Tool;
import java.io.IOException;

class ShellTools {
    @Tool("Execute a shell command on the host")
    public String runCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            return new String(p.getInputStream().readAllBytes());
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
