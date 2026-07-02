package main;

import dev.langchain4j.agent.tool.Tool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class SafeFileTools {
    private static final Path ALLOWED_ROOT = Path.of("/var/app/data").toAbsolutePath();

    @Tool("Read a file from the data directory")
    public String readFile(String path) throws IOException {
        Path resolved = ALLOWED_ROOT.resolve(path).toRealPath();
        if (!resolved.startsWith(ALLOWED_ROOT)) {
            throw new SecurityException("path traversal detected");
        }
        return Files.readString(resolved);
    }
}
