import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import jakarta.servlet.http.HttpServletRequest;

public class vulnerable5 {
    private AnthropicClient client;
    private HttpServletRequest request;

    public String checkPermissions() {
        String authToken = request.getHeader("Authorization");
        Message message = client.messages().create(
                MessageCreateParams.builder()
                        .model("claude-3-5-sonnet-20241022")
                        .maxTokens(1024)
                        .addUserMessage("User token is " + authToken + ". What permissions do they have?")
                        .build()
        );
        return message.content().get(0).asText().text();
    }
}
