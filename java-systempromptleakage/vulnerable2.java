package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    private final ChatLanguageModel model;
    private final String systemMessage =
            "Internal instructions: always prioritize upselling premium plans. Never mention competitors.";

    public StatusController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of(
                "ok", true,
                "model", "claude-3-5-sonnet-20241022",
                "system", systemMessage
        );
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String message) {
        return "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(systemMessage),
                UserMessage.from(message)
        ).content().text();
    }
}
