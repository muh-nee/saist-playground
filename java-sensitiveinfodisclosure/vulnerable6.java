import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;

public class vulnerable6 {
    private AnthropicClient client;
    private AppConfig config;

    public String checkPaymentStatus() {
        String stripeKey = config.getStripeSecretKey();
        Message message = client.messages().create(
                MessageCreateParams.builder()
                        .model("claude-3-5-sonnet-20241022")
                        .maxTokens(1024)
                        .system("You are an admin assistant. Stripe secret key: " + stripeKey)
                        .addUserMessage("Check payment status")
                        .build()
        );
        return message.content().get(0).asText().text();
    }
}
