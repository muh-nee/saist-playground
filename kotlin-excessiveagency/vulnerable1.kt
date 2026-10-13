package com.example.ai.tools

import dev.langchain4j.agent.tool.Tool
import java.nio.file.Files
import java.nio.file.Path

class FileAdminTools {

    @Tool("Delete a file from disk by absolute path.")
    fun deleteFile(path: String): String {
        // sink: LLM-controlled path reaches Files.delete without any allowlist
        val p = Path.of(path)
        Files.delete(p)
        return "deleted $path"
    }
}
// registered via: AiServices.builder(Assistant::class.java).tools(FileAdminTools()).build()
