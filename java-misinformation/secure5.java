import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
public class ClassifyController {
    private final ChatClient chatClient;

    public ClassifyController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/classify")
    public ResponseEntity<Map<String, Boolean>> classify(@RequestParam String text) {
        String label = chatClient.prompt()
            .user("Classify as positive or negative: " + text)
            .call().content();
        boolean isPositive = "positive".equalsIgnoreCase(label.trim());
        return ResponseEntity.ok(Map.of("is_positive", isPositive));
    }
}
