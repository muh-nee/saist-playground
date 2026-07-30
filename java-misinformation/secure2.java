import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChatServlet extends HttpServlet {
    private static final String AI_DISCLAIMER = "Note: AI-generated content. Verify before use.\n\n";
    private final OpenAIClient client = OpenAIOkHttpClient.builder().apiKey("API_KEY").build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String q = req.getParameter("q");
        ChatCompletion completion = client.chat().completions().create(
            ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage(q)
                .build());
        String answer = completion.choices().get(0).message().content().orElse("");
        resp.getWriter().write(AI_DISCLAIMER + answer);
    }
}
