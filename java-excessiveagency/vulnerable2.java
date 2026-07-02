package main;

import dev.langchain4j.agent.tool.Tool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileTools {
    @Tool("Read a file from the filesystem")
    public String readFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
