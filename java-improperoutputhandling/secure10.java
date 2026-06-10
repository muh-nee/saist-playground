// Safe: langchain4j output sanitized by OWASP Java HTML Sanitizer before writing to HTML response
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import javax.servlet.http.HttpServletResponse;

public class secure10 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public void handleRequest(String topic, HttpServletResponse resp) throws Exception {
        String llmContent = model.generate("Write a short article about: " + topic);
        String safe = POLICY.sanitize(llmContent);
        resp.setContentType("text/html");
        resp.getWriter().write("<div>" + safe + "</div>"); // safe: sanitized by OWASP policy
    }
}
