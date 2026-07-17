import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QueryParamIngestController {
    private final VectorStore vectorStore;

    public QueryParamIngestController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/ingest")
    public void ingest(@RequestParam String text) {
        vectorStore.add(List.of(new Document(text)));
    }
}
