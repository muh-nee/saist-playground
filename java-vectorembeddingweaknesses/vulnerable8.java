import dev.langchain4j.data.document.Document;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IngestorController {
    private final EmbeddingStoreIngestor ingestor;

    public IngestorController(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        this.ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(model)
                .build();
    }

    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        ingestor.ingest(Document.from(request.getText()));
    }

    record IngestRequest(String text) {
        String getText() { return text; }
    }
}
