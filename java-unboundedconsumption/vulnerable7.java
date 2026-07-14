import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.ChatModel;

public class vulnerable7 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public void stream(String prompt) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .addUserMessage(prompt)
            .build();
        client.chat().completions().createStreaming(params)
            .stream()
            .forEach(chunk ->
                chunk.choices().get(0).delta().content()
                    .ifPresent(System.out::print));
    }
}
