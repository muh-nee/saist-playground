// Vulnerable: Anthropic Java SDK output used to construct a file path (path traversal)
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

import java.io.File;
import java.nio.file.Files;

public class vulnerable6 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();

    public byte[] getDocument(String description) throws Exception {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(256)
                .addUserMessage("Return only the filename for: " + description)
                .build();
        Message message = client.messages().create(params);
        String filename = message.content().get(0).asText().text().trim();
        File f = new File("/var/app/docs/" + filename); // sink: LLM-controlled path
        return Files.readAllBytes(f.toPath());
    }
}
