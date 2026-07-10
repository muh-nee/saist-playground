package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("model", "gpt-4o");
        debugInfo.put("prompt", systemPrompt);
        return ResponseEntity.ok(debugInfo);
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
