import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Set;

public class secure6 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    private static final Set<String> ALLOWED_HOSTS = Set.of("api.example.com", "docs.example.com");

    public String fetchResource(String description) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o-mini")
                .addUserMessage("Return only the URL for: " + description)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String rawUrl = completion.choices().get(0).message().content().orElse("").trim();
        URL u = new URL(rawUrl);
        if (!ALLOWED_HOSTS.contains(u.getHost())) {
            throw new SecurityException("Host not allowed: " + u.getHost());
        }
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.connect();
        return conn.getResponseMessage();
    }
}
