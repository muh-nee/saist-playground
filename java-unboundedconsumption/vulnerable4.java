import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.ChatModel;

public class vulnerable4 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public String chat(String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .addUserMessage(userMessage)
            .build();
        return client.chat().completions().create(params)
            .choices().get(0).message().content().orElse("");
    }
}
