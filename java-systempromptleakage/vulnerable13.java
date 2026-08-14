import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreRetriever;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class vulnerable13 {

    private final EmbeddingStoreRetriever retriever;
    private final ChatLanguageModel model;

    public vulnerable13(EmbeddingStoreRetriever retriever, ChatLanguageModel model) {
        this.retriever = retriever;
        this.model = model;
    }

    @GetMapping("/context")
    public ResponseEntity<Map<String, Object>> getContext(@RequestParam String query) {
        List<Document> docs = retriever.findRelevant(query, 3);
        String policyText = docs.stream().map(Document::text).collect(Collectors.joining("\n"));
        model.generate(SystemMessage.from(policyText), UserMessage.from(query));
        return ResponseEntity.ok(Map.of("policy", policyText));
    }
}
