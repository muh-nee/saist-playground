import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/ask")
public class AskResource {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey("API_KEY").build();

    @POST
    public Response ask(@QueryParam("question") String question) {
        String rawAnswer = model.generate(question);
        String finalAnswer = rawAnswer;
        return Response.ok(Map.of("answer", finalAnswer)).build();
    }
}
