import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import javax.servlet.http.HttpServletResponse;

public class vulnerable12 {
    private final OpenAiChatModel chatModel;

    public vulnerable12(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public void annotateResponse(String topic, HttpServletResponse resp) {
        ChatResponse response = chatModel.call(new Prompt("Summarize in one line: " + topic));
        String tag = response.getResult().getOutput().getContent();
        resp.setHeader("X-Recommendation", tag); // sink: raw LLM output in response header — CRLF injection
    }
}
