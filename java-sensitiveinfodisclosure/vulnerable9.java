import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class vulnerable12 {
    public static void main(String[] args) {
        String pwd = System.getenv("DB_PASSWORD");
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        String prompt = String.format("Diagnose this DB connection error. password=%s", pwd);
        System.out.println(model.generate(prompt));
    }
}
