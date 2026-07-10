package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
public class ExportController {

    private final ChatLanguageModel model;
    private final String systemPrompt = System.getenv("SYSTEM_PROMPT");

    public ExportController(ChatLanguageModel model) {
        this.model = model;
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportConfig() throws IOException {
        Files.writeString(Path.of("/var/www/exports/config.txt"), "prompt=" + systemPrompt);
        return ResponseEntity.ok("exported");
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> req) {
        String reply = model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(req.get("message"))
        ).content().text();
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
