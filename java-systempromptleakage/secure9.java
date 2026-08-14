import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreRetriever;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class secure9 {

    private final EmbeddingStoreRetriever retriever;
    private final ChatLanguageModel model;

    public secure9(EmbeddingStoreRetriever retriever, ChatLanguageModel model) {
        this.retriever = retriever;
        this.model = model;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestParam String userMessage) {
        List<Document> docs = retriever.findRelevant(userMessage, 3);
        String policyText = docs.stream().map(Document::text).collect(Collectors.joining("\n"));
        var response = model.generate(SystemMessage.from(policyText), UserMessage.from(userMessage));
        return ResponseEntity.ok(Map.of("reply", response.content().text()));
    }
}
