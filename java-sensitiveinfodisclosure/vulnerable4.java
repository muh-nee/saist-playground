import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class vulnerable4 {
    private OpenAIClient client;

    public String debugWebhook() {
        String webhookSecret = "whsec_abc123xyz789";
        ChatCompletion completion = client.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model("gpt-4o")
                        .addUserMessage("Webhook delivery failed. Secret used: " + webhookSecret)
                        .build()
        );
        return completion.choices().get(0).message().content().orElse("");
    }
}
