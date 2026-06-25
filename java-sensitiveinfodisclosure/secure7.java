import org.springframework.ai.chat.client.ChatClient;
import jakarta.servlet.http.HttpServletRequest;

public class secure7 {
    private ChatClient chatClient;
    private HttpServletRequest request;

    public String handleUserQuery() {
        String userQuery = request.getParameter("query");
        return chatClient.prompt()
                .user(userQuery)
                .call()
                .content();
    }
}
