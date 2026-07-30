import org.springframework.ai.chat.client.ChatClient;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AskServlet extends HttpServlet {
    private final ChatClient chatClient;

    public AskServlet(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String question = req.getParameter("question");
        String answer = chatClient.prompt().user(question).call().content();
        resp.setContentType("text/plain");
        resp.getOutputStream().write(answer.getBytes());
    }
}
