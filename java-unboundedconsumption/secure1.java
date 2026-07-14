import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;

public class secure1 {
    private final OpenAiChatModel model = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .modelName("gpt-4o")
        .maxTokens(1024)
        .build();

    public String answer(String userMessage) {
        return model.generate(
            SystemMessage.from("You are a helpful assistant."),
            UserMessage.from(userMessage)
        ).content().text();
    }
}
