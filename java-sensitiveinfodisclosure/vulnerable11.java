import dev.langchain4j.model.chat.ChatLanguageModel;
import java.sql.ResultSet;

public class vulnerable14 {
    private ChatLanguageModel chatLanguageModel;
    private ResultSet resultSet;
    private String issue;

    private String buildPrompt(String name, String email, String ssn) {
        return "Customer " + name + " (email=" + email + ", ssn=" + ssn + ")";
    }

    public String handleTicket() throws Exception {
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String ssn = resultSet.getString("ssn");
        String prompt = buildPrompt(name, email, ssn);
        return chatLanguageModel.generate(prompt + " Issue: " + issue);
    }
}
