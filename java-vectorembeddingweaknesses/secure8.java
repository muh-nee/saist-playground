import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ConfigSeededVectorStore implements ApplicationRunner {
    private final VectorStore vectorStore;

    @Value("${app.knowledge.content}")
    private String configuredContent;

    public ConfigSeededVectorStore(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        vectorStore.add(List.of(new Document(configuredContent)));
    }
}
