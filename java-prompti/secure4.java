import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.util.List;
import java.util.Map;

public class SafeAgentService {

    private static final String SYSTEM_PROMPT = "You are a helpful search assistant.";

    private final SimpleOpenAI client;

    public SafeAgentService(SimpleOpenAI client) {
        this.client = client;
    }

    public String agentTurn(List<ChatMessage> messages, Map<String, Object> toolResult) {
        Object countObj = toolResult.get("result_count");
        if (!(countObj instanceof Integer)) {
            throw new IllegalArgumentException("Unexpected MCP tool output format");
        }
        String safeContent = "Found " + countObj + " results";
        messages.add(ChatMessage.UserMessage.of(safeContent));

        var allMessages = new java.util.ArrayList<ChatMessage>();
        allMessages.add(ChatMessage.SystemMessage.of(SYSTEM_PROMPT));
        allMessages.addAll(messages);

        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(allMessages)
            .build();
        return client.chatCompletions().create(request).join().firstContent();
    }
}
