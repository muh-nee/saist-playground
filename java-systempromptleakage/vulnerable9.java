package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.slf4j.MDC;

public class MdcLoggingService {

    private final ChatLanguageModel model;
    private final String systemPrompt = loadConfig();

    public MdcLoggingService(ChatLanguageModel model) {
        this.model = model;
    }

    public String handleRequest(String requestId, String userMessage) {
        MDC.put("systemPrompt", systemPrompt);
        return model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(userMessage)
        ).content().text();
    }

    private String loadConfig() {
        return "Internal ops assistant. Has access to deployment credentials and CI secrets.";
    }
}
