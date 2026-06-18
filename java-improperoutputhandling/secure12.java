import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class secure12 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private static final Set<String> ALLOWED_DESTINATIONS = Set.of("/dashboard", "/profile", "/settings");

    public void redirect(String userRequest, HttpServletResponse response) throws Exception {
        String target = model.generate("Return only the redirect path for: " + userRequest).trim();
        if (!ALLOWED_DESTINATIONS.contains(target)) {
            throw new SecurityException("Redirect target not allowed: " + target);
        }
        response.sendRedirect(target);
    }
}
