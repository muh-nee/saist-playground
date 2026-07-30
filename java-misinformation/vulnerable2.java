import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
public class AskController {
    private final ChatClient chatClient;

    public AskController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestParam String question) {
        String answer = chatClient.prompt().user(question).call().content();
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
