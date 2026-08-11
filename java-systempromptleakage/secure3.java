package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigSourceController {

    private final ChatLanguageModel model;

    @Value("${app.system-prompt}")
    private String systemPrompt;

    public ConfigSourceController(ChatLanguageModel model) {
        this.model = model;
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String message) {
        return "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(message)
        ).content().text();
    }
}
