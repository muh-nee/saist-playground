import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PathVarIngestController {
    private final VectorStore vectorStore;

    public PathVarIngestController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/ingest/{text}")
    public void ingest(@PathVariable String text) {
        vectorStore.add(List.of(new Document(text)));
    }
}
