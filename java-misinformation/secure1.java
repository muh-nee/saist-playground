import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
public class AskController {
    private static final String DISCLAIMER = "AI-generated content. Verify independently before acting on this information.";
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey("API_KEY").build();

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestParam String question) {
        String answer = model.generate(question);
        return ResponseEntity.ok(Map.of("answer", answer, "disclaimer", DISCLAIMER));
    }
}
