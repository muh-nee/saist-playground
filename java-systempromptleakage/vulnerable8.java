package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DiagnosticController {

    private final ChatLanguageModel model;
    private final String systemPrompt =
            "Confidential: billing assistant with access to payment processor API keys.";

    public DiagnosticController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug() {
        return ResponseEntity.ok(Map.of(
                "model", "gpt-4o",
                "prompt", systemPrompt
        ));
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String message) {
        return model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(message)
        ).content().text();
    }
}
