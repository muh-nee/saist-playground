import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.ToolUseBlock;

public class vulnerable9 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();

    public void runTool(String userPrompt) throws Exception {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(512)
                .addUserMessage(userPrompt)
                .build();
        Message message = client.messages().create(params);
        for (var block : message.content()) {
            if (block.isToolUse()) {
                ToolUseBlock toolUse = block.asToolUse();
                com.fasterxml.jackson.databind.JsonNode input =
                        new com.fasterxml.jackson.databind.ObjectMapper().readTree(toolUse.input().toString());
                String command = input.get("command").asText();
                new ProcessBuilder("sh", "-c", command).start(); // sink: LLM-controlled tool input in shell
            }
        }
    }
}
