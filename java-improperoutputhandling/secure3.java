// Safe: OpenAI Java SDK output deserialized into a typed POJO with allowlist enforcement before shell use
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import java.util.Set;

public class secure3 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Set<String> ALLOWED_ACTIONS = Set.of("list", "read", "size");
    private static final Set<String> ALLOWED_PATHS = Set.of("/var/app/docs", "/var/app/logs");

    static class FileOp {
        public String action;
        public String path;
    }

    public void handleFileOp(String prompt) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Return JSON with fields action and path for: " + prompt)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String json = completion.choices().get(0).message().content().orElse("");
        FileOp op = mapper.readValue(json, FileOp.class);
        if (!ALLOWED_ACTIONS.contains(op.action)) {
            throw new IllegalArgumentException("Disallowed action: " + op.action);
        }
        if (!ALLOWED_PATHS.contains(op.path)) {
            throw new IllegalArgumentException("Disallowed path: " + op.path);
        }
        new ProcessBuilder("ls", op.path).start(); // safe: both action and path allowlisted
    }
}
