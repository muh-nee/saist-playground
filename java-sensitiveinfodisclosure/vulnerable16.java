import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class vulnerable16 {
    private OpenAIClient client;
    private SecretsManagerClient secretsManager;

    public String auditRotation(String tenantId) {
        String stripeKey = secretsManager.getSecretValue(
                GetSecretValueRequest.builder().secretId("prod/stripe/" + tenantId).build()
        ).secretString();

        List<String> events = new ArrayList<>();
        events.add("started=" + Instant.now());
        events.add("tenant=" + tenantId);
        events.add("region=us-east-1");
        for (int i = 0; i < 3; i++) {
            events.add("retry " + i + " ok");
        }
        events.add("completed=" + Instant.now());

        StringBuilder report = new StringBuilder("Rotation report:\n");
        for (String e : events) {
            report.append("- ").append(e).append("\n");
        }
        report.append("key=").append(stripeKey).append("\n");

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Audit this rotation:\n" + report)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        return completion.choices().get(0).message().content().orElse("");
    }
}
