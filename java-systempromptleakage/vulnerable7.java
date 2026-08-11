package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LlmController {

    private final ChatLanguageModel model;
    private final String systemInstructions =
            "You assist with HR queries. Employee salary data is available to you.";

    public LlmController(ChatLanguageModel model) {
        this.model = model;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        System.out.println("[DEBUG] systemInstructions=" + systemInstructions);
        return "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(systemInstructions),
                UserMessage.from(message)
        ).content().text();
    }
}
