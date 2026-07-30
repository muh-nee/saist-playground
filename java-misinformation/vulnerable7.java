import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
public class MedicalController {
    private final OpenAIClient client = OpenAIOkHttpClient.builder().apiKey("API_KEY").build();

    @GetMapping("/advice")
    public ResponseEntity<Map<String, String>> getAdvice(@RequestParam String symptom) {
        ChatCompletion completion = client.chat().completions().create(
            ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Medical advice for: " + symptom)
                .build());
        String advice = completion.choices().get(0).message().content().orElse("");
        return ResponseEntity.ok(Map.of("advice", advice));
    }
}
