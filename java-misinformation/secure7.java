import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SummaryLoader {
    private final OpenAIClient client = OpenAIOkHttpClient.builder().apiKey("API_KEY").build();
    private String cachedSummary;

    @PostConstruct
    public void init() {
        ChatCompletion completion = client.chat().completions().create(
            ChatCompletionCreateParams.builder()
                .model("gpt-4o-mini")
                .addUserMessage("Summarize today's news in one sentence.")
                .build());
        cachedSummary = completion.choices().get(0).message().content().orElse("");
    }
}
