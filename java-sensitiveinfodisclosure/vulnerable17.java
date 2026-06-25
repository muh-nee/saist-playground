import dev.langchain4j.model.chat.ChatLanguageModel;
import java.sql.ResultSet;
import java.util.List;

public class vulnerable17 {
    private ChatLanguageModel model;
    private ResultSet resultSet;

    public String summarizeRisk() throws Exception {
        String email = resultSet.getString("email");
        String ssn = resultSet.getString("ssn");
        List<String> context = List.of("user=" + email, "ssn=" + ssn);
        return model.generate("Context: " + context + ". Summarize risk profile.");
    }
}
