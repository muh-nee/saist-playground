import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.net.http.HttpRequest;

public class secure6 {
    private AppConfig config;

    public String diagnose(HttpRequest.Builder reqBuilder) {
        reqBuilder.header("Authorization", "Bearer " + config.getSecretKey());
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        return model.generate(
                "Diagnose issue in " + config.getEnvironment() + " (" + config.getRegion() + ")."
        );
    }
}
