import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import java.util.List;

public class vulnerable10 {
    private BedrockRuntimeClient bedrockClient;

    public String rotateKey() {
        String apiKey = System.getenv("PAYMENT_API_KEY");
        Message userMessage = Message.builder()
                .role(ConversationRole.USER)
                .content(List.of(ContentBlock.fromText("Rotation failed for key: " + apiKey + ". Diagnose.")))
                .build();
        return bedrockClient.converse(
                ConverseRequest.builder()
                        .modelId("anthropic.claude-3-5-sonnet-20241022-v2:0")
                        .messages(userMessage)
                        .build()
        ).output().message().content().get(0).text();
    }
}
