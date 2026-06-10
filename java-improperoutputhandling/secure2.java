// Safe: Spring AI output validated against an allowlist before use in SQL
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

public class secure2 {
    private final OpenAiChatModel chatModel;
    private final JdbcTemplate jdbcTemplate;
    private static final Set<String> ALLOWED_TABLES = Set.of("users", "orders", "products");

    public secure2(OpenAiChatModel chatModel, JdbcTemplate jdbcTemplate) {
        this.chatModel = chatModel;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<?> queryTable(String question) {
        ChatResponse response = chatModel.call(new Prompt("Reply with only the table name relevant to: " + question));
        String tableName = response.getResult().getOutput().getContent().trim().toLowerCase();
        if (!ALLOWED_TABLES.contains(tableName)) {
            throw new IllegalArgumentException("Unknown table: " + tableName);
        }
        return jdbcTemplate.queryForList("SELECT * FROM " + tableName + " WHERE active = ?", true); // safe: allowlist enforced
    }
}
