// Safe: Anthropic Java SDK output validated against an allowlist before JNDI lookup
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Set;

public class secure13 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private static final Set<String> ALLOWED_NAMES = Set.of(
            "java:comp/env/jdbc/primary",
            "java:comp/env/jdbc/replica",
            "java:comp/env/mail/Session"
    );

    public Object resolveResource(String description) throws Exception {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(64)
                .addUserMessage("Return only the JNDI resource name for: " + description)
                .build();
        Message message = client.messages().create(params);
        String name = message.content().get(0).asText().text().trim();
        if (!ALLOWED_NAMES.contains(name)) {
            throw new SecurityException("JNDI name not allowed: " + name);
        }
        Context ctx = new InitialContext();
        return ctx.lookup(name); // safe: lookup name allowlisted — no remote class loading
    }
}
