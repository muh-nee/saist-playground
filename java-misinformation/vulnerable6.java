import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AskServlet extends HttpServlet {
    private final ChatModel chatModel;

    public AskServlet(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String question = req.getParameter("question");
        String answer = chatModel.call(new Prompt(question)).getResult().getOutput().getText();
        resp.setContentType("application/json");
        resp.getWriter().write("{\"answer\":\"" + answer + "\"}");
    }
}
