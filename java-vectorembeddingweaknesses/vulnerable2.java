import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BatchIngestController {
    private final EmbeddingStore<TextSegment> store;
    private final EmbeddingModel model;

    public BatchIngestController(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.store = store;
        this.model = model;
    }

    @PostMapping("/ingest/batch")
    public void batchIngest(@RequestBody List<String> texts) {
        List<TextSegment> segments = texts.stream().map(TextSegment::from).collect(Collectors.toList());
        List<Embedding> embeddings = model.embedAll(segments).content();
        store.addAll(embeddings, segments);
    }
}
