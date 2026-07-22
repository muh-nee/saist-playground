import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IngestController {
    private final EmbeddingStore<TextSegment> store;
    private final EmbeddingModel model;

    public IngestController(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.store = store;
        this.model = model;
    }

    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        TextSegment segment = TextSegment.from(request.getText());
        Embedding embedding = model.embed(segment).content();
        store.add(embedding, segment);
    }

    record IngestRequest(String text) {
        String getText() { return text; }
    }
}
