import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class secure5 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private static final Path BASE_DIR = Paths.get("/var/app/docs");

    public byte[] getDocument(String description) throws Exception {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(256)
                .addUserMessage("Return only the filename for: " + description)
                .build();
        Message message = client.messages().create(params);
        String filename = message.content().get(0).asText().text().trim();
        Path resolved = BASE_DIR.resolve(filename).normalize();
        if (!resolved.startsWith(BASE_DIR)) {
            throw new SecurityException("Path escapes base directory");
        }
        return Files.readAllBytes(resolved);
    }
}
