import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.ChatModel;

public class vulnerable9 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public String respond(String systemPrompt, String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .addSystemMessage(systemPrompt)
            .addUserMessage(userMessage)
            .build();
        return client.chat().completions().create(params)
            .choices().get(0).message().content().orElse("");
    }
}
