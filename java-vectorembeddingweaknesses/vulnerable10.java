import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ServiceIngestController {
    private final IngestService ingestService;

    public ServiceIngestController(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.ingestService = new IngestService(store, model);
    }

    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        ingestService.store(request.getText());
    }

    record IngestRequest(String text) {
        String getText() { return text; }
    }
}

class IngestService {
    private final EmbeddingStore<TextSegment> store;
    private final EmbeddingModel model;

    IngestService(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.store = store;
        this.model = model;
    }

    void store(String text) {
        TextSegment segment = TextSegment.from(text);
        Embedding embedding = model.embed(segment).content();
        store.add(embedding, segment);
    }
}
