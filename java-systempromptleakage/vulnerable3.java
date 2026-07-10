package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatLanguageModel model;
    private final String systemPrompt = loadPrompt("prompts/assistant.txt");

    public ChatService(ChatLanguageModel model) {
        this.model = model;
    }

    public String handleChat(String userMessage) {
        log.info("Sending LLM request with system prompt: {}", systemPrompt);
        return model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(userMessage)
        ).content().text();
    }

    private String loadPrompt(String path) {
        return "Internal support agent. Has access to HR records and payroll data.";
    }
}
