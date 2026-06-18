import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class vulnerable15 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public Object loadHandler(String description) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Return only the fully qualified class name for a handler that: " + description)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String className = completion.choices().get(0).message().content().orElse("").trim();
        return Class.forName(className).getDeclaredConstructor().newInstance(); // sink: LLM-controlled class instantiation
    }
}
