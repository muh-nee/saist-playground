package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataLoggingService {

    private static final Logger log = LoggerFactory.getLogger(MetadataLoggingService.class);
    private final ChatLanguageModel model;
    private final String systemPrompt = loadPrompt("prompts/assistant.txt");

    public MetadataLoggingService(ChatLanguageModel model) {
        this.model = model;
    }

    public String handleChat(String userMessage) {
        log.info("Prompt loaded, length={}", systemPrompt.length());
        return model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(userMessage)
        ).content().text();
    }

    private String loadPrompt(String path) {
        return "Proprietary assistant instructions loaded from config.";
    }
}
