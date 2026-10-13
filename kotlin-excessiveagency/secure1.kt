package com.example.ai.tools

import dev.langchain4j.agent.tool.Tool
import java.nio.file.Files
import java.nio.file.Path

class FileAdminTools {

    private val allowedRoot: Path = Path.of("/var/app/scratch").toRealPath()

    @Tool("Delete a scratch file by name under the application scratch directory.")
    fun deleteFile(name: String): String {
        val resolved = allowedRoot.resolve(name).normalize().toRealPath()
        require(resolved.startsWith(allowedRoot)) { "path escape rejected" }
        // safe: path confined to a developer-controlled root
        Files.delete(resolved)
        return "deleted $name"
    }
}
