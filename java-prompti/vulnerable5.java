import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.util.List;
import java.util.Map;

public class AgentService {

    private final SimpleOpenAI client;

    public AgentService(SimpleOpenAI client) {
        this.client = client;
    }

    public String agentTurn(List<ChatMessage> messages, Map<String, Object> toolResult) {
        String toolOutput = (String) toolResult.get("output");
        messages.add(ChatMessage.UserMessage.of(toolOutput));

        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(messages)
            .build();
        return client.chatCompletions().create(request).join().firstContent();
    }
}
