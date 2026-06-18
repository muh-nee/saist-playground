import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class secure14 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private final JdbcTemplate jdbcTemplate;

    public secure14(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> searchProducts(String description) {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(256)
                .addUserMessage("Extract only the search term from: " + description)
                .build();
        Message message = client.messages().create(params);
        String searchTerm = message.content().get(0).asText().text();
        return jdbcTemplate.queryForList("SELECT * FROM products WHERE name LIKE ?", "%" + searchTerm + "%");
    }
}
