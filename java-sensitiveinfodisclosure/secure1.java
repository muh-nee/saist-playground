import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class secure1 {
    public static void main(String[] args) {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        String response = model.generate("Summarize this document.");
        System.out.println(response);
    }
}
