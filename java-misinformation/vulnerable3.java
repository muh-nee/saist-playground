import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
public class AskController {
    private final OpenAIClient client = OpenAIOkHttpClient.builder().apiKey("API_KEY").build();

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestParam String question) {
        ChatCompletion completion = client.chat().completions().create(
            ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage(question)
                .build());
        String answer = completion.choices().get(0).message().content().orElse("");
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
