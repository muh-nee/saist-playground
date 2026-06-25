import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.sql.ResultSet;

public class secure5 {
    private ChatLanguageModel chatLanguageModel;
    private ResultSet resultSet;
    private String issue;

    private String buildPrompt(String name, String email, String region) {
        return "Customer region: " + region + ".";
    }

    public String handleTicket() throws Exception {
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String region = resultSet.getString("region");
        String prompt = buildPrompt(name, email, region);
        return chatLanguageModel.generate(prompt + " Issue: " + issue);
    }
}
