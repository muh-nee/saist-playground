import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class vulnerable3 {
    public OpenAiChatModel buildModel() {
        OpenAiApi api = OpenAiApi.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .build();
        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model("gpt-4o")
            .build();
        return new OpenAiChatModel(api, options);
    }
}
