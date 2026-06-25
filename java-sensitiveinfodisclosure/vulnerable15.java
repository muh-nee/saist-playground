import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class vulnerable15 {
    private boolean debug;

    public String diagnose() {
        String dbPassword = System.getenv("DB_PASSWORD");
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        String prompt = "Diagnose this DB connection error.";
        if (debug) {
            prompt = prompt + " password=" + dbPassword;
        }
        return model.generate(prompt);
    }
}
