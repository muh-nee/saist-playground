import org.springframework.ai.chat.client.ChatClient;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/ask")
public class AskResource {
    private static final String AI_WARNING = "This response is AI-generated and may contain inaccuracies.";
    private final ChatClient chatClient;

    public AskResource(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @POST
    public Response ask(@QueryParam("question") String question) {
        String answer = chatClient.prompt().user(question).call().content();
        String safeResponse = AI_WARNING + "\n\n" + answer;
        return Response.ok(Map.of("answer", safeResponse)).build();
    }
}
