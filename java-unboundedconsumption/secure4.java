import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

public class secure4 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();

    public String chat(String userMessage) {
        MessageCreateParams params = MessageCreateParams.builder()
            .model(Model.CLAUDE_SONNET_4_5_20250929)
            .maxTokens(1024L)
            .addUserMessage(userMessage)
            .build();
        return client.messages().create(params).content().get(0).text().text();
    }
}
