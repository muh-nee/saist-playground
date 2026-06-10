// Vulnerable: Anthropic Java SDK output executed directly via JdbcTemplate.execute()
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.jdbc.core.JdbcTemplate;

public class vulnerable14 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private final JdbcTemplate jdbcTemplate;

    public vulnerable14(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void applyMigration(String description) {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(512)
                .addUserMessage("Generate a SQL migration statement for: " + description)
                .build();
        Message message = client.messages().create(params);
        String sql = message.content().get(0).asText().text();
        jdbcTemplate.execute(sql); // sink: raw LLM output in SQL
    }
}
