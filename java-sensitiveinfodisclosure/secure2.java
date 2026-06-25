import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import java.sql.ResultSet;

public class secure2 {
    private ChatModel chatModel;
    private ResultSet resultSet;
    private long userId;

    public String summarizeAccount() throws Exception {
        String email = resultSet.getString("email");
        String ssn = resultSet.getString("ssn");
        String subscriptionTier = resultSet.getString("subscription_tier");
        ChatResponse response = chatModel.call(
                new Prompt("Summarize activity for user ID " + userId + " on tier " + subscriptionTier + ".")
        );
        return response.getResult().getOutput().getContent();
    }
}
