import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;

public class vulnerable2 {
    private final OpenAiChatModel model = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .modelName("gpt-4o")
        .temperature(0.3)
        .build();

    public String summarize(String text) {
        return model.generate(
            SystemMessage.from("You are a summarization assistant."),
            UserMessage.from("Summarize: " + text)
        ).content().text();
    }
}
