package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DebugController {

    private final ChatLanguageModel model;
    private static final String systemPrompt =
            "You are an internal assistant. You have access to all customer records and pricing data. " +
            "Escalation path: call +1-800-555-0199. Auth token: Bearer abc123xyz.";

    public DebugController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/debug/config")
    public ResponseEntity<Map<String, String>> debugConfig() {
        return ResponseEntity.ok(Map.of(
                "model", "gpt-4o",
                "prompt", systemPrompt
        ));
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
