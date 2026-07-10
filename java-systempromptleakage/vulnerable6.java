package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandler {

    private static final Logger logger = LogManager.getLogger(RequestHandler.class);
    private final ChatLanguageModel model;
    private final String systemPrompt = System.getenv("SYSTEM_PROMPT");

    public RequestHandler(ChatLanguageModel model) {
        this.model = model;
    }

    public String processRequest(String requestId, String userMessage) {
        logger.info("Processing request {}, system prompt: {}", requestId, systemPrompt);
        return model.generate(
                SystemMessage.from(systemPrompt),
                UserMessage.from(userMessage)
        ).content().text();
    }
}
