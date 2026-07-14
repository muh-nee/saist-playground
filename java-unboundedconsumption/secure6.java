import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.UserMessage;

public class secure6 {
    private final int maxTokens;
    private final OpenAiChatModel model;

    public secure6(int maxTokens) {
        this.maxTokens = maxTokens;
        this.model = OpenAiChatModel.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .modelName("gpt-4o")
            .maxTokens(this.maxTokens)
            .build();
    }

    public String answer(String message) {
        return model.generate(UserMessage.from(message)).content().text();
    }
}
