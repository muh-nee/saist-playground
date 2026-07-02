package main;

import dev.langchain4j.agent.tool.Tool;
import java.io.IOException;
import java.util.Set;

class DiagnosticTools {
    private static final Set<String> ALLOWED = Set.of("df", "free", "uptime", "hostname");

    @Tool("Run an approved diagnostic command")
    public String runDiagnostic(String command) throws IOException {
        if (!ALLOWED.contains(command)) {
            throw new IllegalArgumentException("command not in allowlist");
        }
        Process p = new ProcessBuilder(command).start();
        return new String(p.getInputStream().readAllBytes());
    }
}
