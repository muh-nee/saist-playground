package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private final ChatLanguageModel model;
    private static final String SYSTEM_PROMPT =
            "Internal tool. Access restricted. Contact admin@corp.internal for issues.";

    public ConfigController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/config")
    public String config() {
        return "Active prompt: " + SYSTEM_PROMPT;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(SYSTEM_PROMPT),
                UserMessage.from(message)
        ).content().text();
    }
}
