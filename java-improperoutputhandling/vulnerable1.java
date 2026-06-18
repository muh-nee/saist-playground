import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class vulnerable1 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private final Connection conn;

    public vulnerable1(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getReport(String userQuery) throws Exception {
        String sqlQuery = model.generate("Generate a SQL query for: " + userQuery);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sqlQuery); // sink: raw LLM output in SQL
    }
}
