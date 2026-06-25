import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import java.security.MessageDigest;
import java.util.Base64;
import java.sql.ResultSet;

public class secure4 {
    private OpenAIClient client;
    private ResultSet resultSet;

    public String analyzeActivity() throws Exception {
        String email = resultSet.getString("email");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String emailHash = Base64.getEncoder().encodeToString(md.digest(email.getBytes()));
        ChatCompletion completion = client.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model("gpt-4o")
                        .addUserMessage("Analyze activity for user hash " + emailHash)
                        .build()
        );
        return completion.choices().get(0).message().content().orElse("");
    }
}
