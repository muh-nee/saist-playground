import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

public class secure3 {
    public String listTables() {
        String dbPassword = System.getenv("DB_PASSWORD");
        DataSource ds = DataSourceBuilder.create().password(dbPassword).build();
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        return model.generate("List available database tables.");
    }
}
