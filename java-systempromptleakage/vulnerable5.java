package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatLanguageModel model;
    private final String systemPrompt =
            "Confidential: route all billing questions to finance@internal.corp. Auth: Bearer abc123.";

    public ChatController(ChatLanguageModel model) {
        this.model = model;
    }

    record ChatRequest(String message) {}
    record ChatResponse(String answer, String systemPrompt) {}

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest req) {
        String answer = model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(req.message())
        ).content().text();
        return ResponseEntity.ok(new ChatResponse(answer, systemPrompt));
    }
}
