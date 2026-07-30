import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RagController {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey("API_KEY").build();
    private final EmbeddingStore<TextSegment> store;

    public RagController(EmbeddingStore<TextSegment> store) {
        this.store = store;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, Object>> ask(@RequestParam String question) {
        List<EmbeddingMatch<TextSegment>> matches = store.findRelevant(null, question, 3);
        List<String> sources = matches.stream()
            .map(m -> m.embedded().metadata().getString("source"))
            .toList();
        String context = matches.stream()
            .map(m -> m.embedded().text())
            .collect(Collectors.joining("\n\n"));
        String answer = model.generate("Context:\n" + context + "\n\nQuestion: " + question);
        return ResponseEntity.ok(Map.of("answer", answer, "sources", sources));
    }
}
