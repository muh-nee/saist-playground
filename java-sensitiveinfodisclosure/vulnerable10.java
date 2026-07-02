import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

public class vulnerable13 {
    private SecretsManagerClient secretsManager;

    public String rotateStripeKey() {
        String stripeKey = secretsManager.getSecretValue(
                GetSecretValueRequest.builder().secretId("prod/stripe").build()
        ).secretString();
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        return model.generate("Rotate this Stripe key: " + stripeKey);
    }
}
