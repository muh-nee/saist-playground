import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AllowlistIngestController {
    private static final Set<String> ALLOWED = Set.of("news", "docs", "faq", "tutorials");
    private final EmbeddingStore<TextSegment> store;
    private final EmbeddingModel model;

    public AllowlistIngestController(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.store = store;
        this.model = model;
    }

    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        if (!ALLOWED.contains(request.getCategory())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category");
        }
        TextSegment segment = TextSegment.from(request.getCategory());
        Embedding embedding = model.embed(segment).content();
        store.add(embedding, segment);
    }

    record IngestRequest(String category) {
        String getCategory() { return category; }
    }
}
