import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.UserMessage;

public class vulnerable10 {
    private final OpenAiChatModel model = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .modelName("gpt-4o")
        .build();

    public String draft(String topic) {
        return model.generate(UserMessage.from("Draft: " + topic)).content().text();
    }

    public String review(String draft) {
        return model.generate(UserMessage.from("Review: " + draft)).content().text();
    }
}
