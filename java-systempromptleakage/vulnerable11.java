package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class ExportController {

    private final ChatLanguageModel model;
    private final String systemPrompt = System.getenv("SYSTEM_PROMPT");

    public ExportController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/export")
    public String exportConfig() throws IOException {
        Files.writeString(Path.of("/var/www/exports/config.txt"), "prompt=" + systemPrompt);
        return "exported";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String message) {
        return "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(message)
        ).content().text();
    }
}
