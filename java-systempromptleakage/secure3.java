package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigSourceController {

    private final ChatLanguageModel model;

    @Value("${app.system-prompt}")
    private String systemPrompt;

    public ConfigSourceController(ChatLanguageModel model) {
        this.model = model;
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
