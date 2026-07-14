import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class vulnerable5 {
    interface Assistant {
        String chat(String message);
    }

    public Assistant buildAssistant() {
        OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .modelName("gpt-4o-mini")
            .build();
        return AiServices.builder(Assistant.class)
            .chatLanguageModel(model)
            .build();
    }
}
