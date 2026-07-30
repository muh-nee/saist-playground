import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ReviewController {
    private final AnthropicClient client = AnthropicOkHttpClient.builder().apiKey("API_KEY").build();
    private final ConcurrentHashMap<String, String> pending = new ConcurrentHashMap<>();

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestParam String question) {
        Message message = client.messages().create(
            MessageCreateParams.builder()
                .model("claude-opus-4-5")
                .maxTokens(4096)
                .addUserMessage(question)
                .build());
        String answer = message.content().get(0).asText().text();
        pending.put("pending-1", answer);
        return ResponseEntity.ok(Map.of("status", "pending_review", "id", "pending-1"));
    }
}
