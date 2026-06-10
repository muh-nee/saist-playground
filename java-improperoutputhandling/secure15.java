// Safe: Spring AI output validated before use — data flow never reaches a dangerous sink
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import javax.servlet.http.HttpServletResponse;

public class secure15 {
    private final OpenAiChatModel chatModel;

    public secure15(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public void respondWithSummary(String topic, HttpServletResponse resp) throws Exception {
        ChatResponse response = chatModel.call(new Prompt("Summarize in plain text: " + topic));
        String summary = response.getResult().getOutput().getContent();
        resp.setContentType("application/json"); // explicitly JSON, not HTML — no XSS sink
        resp.getWriter().write("{\"summary\": \"" + summary.replace("\"", "\\\"") + "\"}");
    }
}
