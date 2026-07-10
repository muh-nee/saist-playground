package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LlmOutputController {

    private final ChatLanguageModel model;
    private final String systemPrompt = "You are a helpful assistant.";

    public LlmOutputController(ChatLanguageModel model) {
        this.model = model;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> req) {
        String llmOutput = model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(req.get("message"))
        ).content().text();
        return ResponseEntity.ok(Map.of("reply", llmOutput));
    }
}
