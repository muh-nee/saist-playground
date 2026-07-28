import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EphemeralStoreController {
    private final EmbeddingModel model;

    public EphemeralStoreController(EmbeddingModel model) {
        this.model = model;
    }

    @PostMapping("/search")
    public String search(@RequestBody SearchRequest request) {
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        TextSegment segment = TextSegment.from("Go programming guide");
        Embedding embedding = model.embed(segment).content();
        store.add(embedding, segment);
        return store.findRelevant(model.embed(TextSegment.from(request.getQuery())).content(), 1).toString();
    }

    record SearchRequest(String query) {
        String getQuery() { return query; }
    }
}
