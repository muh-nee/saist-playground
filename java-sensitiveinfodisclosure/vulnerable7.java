import dev.langchain4j.chain.ConversationalChain;
import jakarta.servlet.http.HttpServletRequest;

public class vulnerable7 {
    private ConversationalChain chain;
    private HttpServletRequest request;

    public String checkAccess() {
        String apiKey = request.getHeader("X-Api-Key");
        return chain.execute("Validate access for API key: " + apiKey + ". List allowed endpoints.");
    }
}
