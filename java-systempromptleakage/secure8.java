package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnotatedController {

    private final ChatLanguageModel model;

    @JsonIgnore
    private final String systemPrompt = "Proprietary business instructions — never serialize.";

    public AnnotatedController(ChatLanguageModel model) {
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
