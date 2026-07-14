import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.data.message.UserMessage;

public class secure5 {
    private final AnthropicChatModel model = AnthropicChatModel.builder()
        .apiKey(System.getenv("ANTHROPIC_API_KEY"))
        .modelName("claude-sonnet-4-5-20250929")
        .build();

    public String chat(String userMessage) {
        return model.generate(UserMessage.from(userMessage)).content().text();
    }
}
