// Safe: Anthropic tool-use response deserialized into a typed POJO with allowlist before shell use
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.ToolUseBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

public class secure13 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Set<String> ALLOWED_COMMANDS = Set.of("ls", "pwd", "whoami");

    static class ToolInput {
        public String command;
    }

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
                com.fasterxml.jackson.databind.JsonNode inputNode = mapper.readTree(toolUse.input().toString());
                ToolInput input = mapper.treeToValue(inputNode, ToolInput.class);
                if (!ALLOWED_COMMANDS.contains(input.command)) {
                    throw new SecurityException("Command not allowed: " + input.command);
                }
                new ProcessBuilder(input.command).start(); // safe: command allowlisted
            }
        }
    }
}
