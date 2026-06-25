import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import java.sql.ResultSet;

public class vulnerable2 {
    private ChatModel chatModel;
    private ResultSet resultSet;

    public String summarizeAccount() throws Exception {
        String email = resultSet.getString("email");
        String ssn = resultSet.getString("ssn");
        ChatResponse response = chatModel.call(
                new Prompt("Summarize account activity for " + email + " (SSN: " + ssn + ")")
        );
        return response.getResult().getOutput().getContent();
    }
}
