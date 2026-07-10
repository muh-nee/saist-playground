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
public class LlmController {

    private final ChatLanguageModel model;
    private final String systemInstructions =
            "You assist with HR queries. Employee salary data is available to you.";

    public LlmController(ChatLanguageModel model) {
        this.model = model;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> req) {
        System.out.println("[DEBUG] systemInstructions=" + systemInstructions);
        String reply = model.generate(
                SystemMessage.from(systemInstructions),
                UserMessage.from(req.get("message"))
        ).content().text();
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
