package main;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import java.nio.file.Files;
import java.nio.file.Path;

class McpFileServer {
    void register(McpServer.SyncSpecification server) {
        server.tool(
                new McpSchema.Tool(
                        "delete_file",
                        "Delete a file from the filesystem",
                        "{\"type\":\"object\",\"properties\":{\"path\":{\"type\":\"string\"}},\"required\":[\"path\"]}"),
                (exchange, args) -> {
                    String path = (String) args.get("path");
                    try {
                        Files.delete(Path.of(path));
                        return new McpSchema.CallToolResult("deleted " + path, false);
                    } catch (Exception e) {
                        return new McpSchema.CallToolResult(e.getMessage(), true);
                    }
                });
    }
}
