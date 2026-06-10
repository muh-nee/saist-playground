// Safe: langchain4j output HTML-escaped via Spring's HtmlUtils before writing to HTML response
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;

public class secure4 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();

    public void handleRequest(String topic, HttpServletResponse resp) throws Exception {
        String llmContent = model.generate("Write a short summary about: " + topic);
        String safe = HtmlUtils.htmlEscape(llmContent);
        resp.setContentType("text/html");
        resp.getWriter().write("<div>" + safe + "</div>"); // safe: HTML-escaped before output
    }
}
