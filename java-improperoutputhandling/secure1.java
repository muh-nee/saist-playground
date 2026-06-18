import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class secure1 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private final Connection conn;

    public secure1(Connection conn) {
        this.conn = conn;
    }

    public ResultSet searchUsers(String userQuery) throws Exception {
        String searchTerm = model.generate("Extract only the search term from: " + userQuery);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        ps.setString(1, searchTerm);
        return ps.executeQuery();
    }
}
