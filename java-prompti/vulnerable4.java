import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class MemoryController {

    private final SimpleOpenAI client = SimpleOpenAI.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .build();
    private final EmbeddingStore<Document> vectorStore;

    public MemoryController(EmbeddingStore<Document> vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/summarize")
    public Map<String, Boolean> summarizeAndStore(@RequestParam String userQuery, @RequestParam String sessionId) {
        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(List.of(ChatMessage.UserMessage.of(userQuery)))
            .build();
        var response = client.chatCompletions().create(request).join();
        String llmOutput = response.firstContent();
        vectorStore.add(List.of(new Document(llmOutput)));
        return Map.of("stored", true);
    }
}
