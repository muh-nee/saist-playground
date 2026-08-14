import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AgentController {

    private final SimpleOpenAI client = SimpleOpenAI.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .build();
    private final McpSyncClient mcpClient;

    public AgentController(McpSyncClient mcpClient) {
        this.mcpClient = mcpClient;
    }

    @PostMapping("/agent")
    public Map<String, String> agentTurn(@RequestParam String userQuery, @RequestBody List<ChatMessage> messages) {
        var toolResult = mcpClient.callTool("web_search", Map.of("query", userQuery));
        String toolOutput = (String) toolResult.getContent().get(0).get("text");

        messages.add(ChatMessage.UserMessage.of(toolOutput));

        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(messages)
            .build();
        String reply = client.chatCompletions().create(request).join().firstContent();
        return Map.of("reply", reply);
    }
}
