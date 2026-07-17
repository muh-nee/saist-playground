import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LlmResponseIngestController {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public LlmResponseIngestController(VectorStore vectorStore, ChatClient.Builder builder) {
        this.vectorStore = vectorStore;
        this.chatClient = builder.build();
    }

    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        String modelOutput = chatClient.prompt()
                .user(request.getQuestion())
                .call()
                .content();
        vectorStore.add(List.of(new Document(modelOutput)));
    }

    record IngestRequest(String question) {
        String getQuestion() { return question; }
    }
}
