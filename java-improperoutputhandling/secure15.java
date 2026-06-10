// Safe: Spring AI output embedded in JSON response via Jackson serialization (correct escaping for all chars)
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class secure15 {
    private final OpenAiChatModel chatModel;
    private final ObjectMapper mapper = new ObjectMapper();

    public secure15(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public void respondWithSummary(String topic, HttpServletResponse resp) throws Exception {
        ChatResponse response = chatModel.call(new Prompt("Summarize in plain text: " + topic));
        String summary = response.getResult().getOutput().getContent();
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), Map.of("summary", summary)); // safe: Jackson handles all JSON escaping
    }
}
