import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminIngestController {
    private final VectorStore vectorStore;

    public AdminIngestController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ingest")
    public void ingest(@RequestBody IngestRequest request) {
        vectorStore.add(List.of(new Document(request.getText())));
    }

    record IngestRequest(String text) {
        String getText() { return text; }
    }
}
