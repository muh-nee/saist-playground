import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class secure2 {
    public OpenAiChatModel buildModel() {
        OpenAiApi api = OpenAiApi.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .build();
        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model("gpt-4o")
            .maxTokens(2048)
            .build();
        return new OpenAiChatModel(api, options);
    }
}
