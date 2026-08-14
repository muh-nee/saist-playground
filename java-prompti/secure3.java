import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.util.List;
import java.util.regex.Pattern;

public class SafeMemoryService {

    private static final Pattern INJECTION_RE = Pattern.compile(
        "(?i)(ignore (all |previous )?instructions?|you are now|system:)"
    );
    private static final Pattern CONTROL_RE = Pattern.compile("<\\|[^|]*\\|>");

    private final SimpleOpenAI client;
    private final EmbeddingStore<Document> vectorStore;

    public SafeMemoryService(SimpleOpenAI client, EmbeddingStore<Document> vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }

    private static String sanitize(String text) {
        text = INJECTION_RE.matcher(text).replaceAll("");
        text = CONTROL_RE.matcher(text).replaceAll("");
        return text.strip();
    }

    public void summarizeAndStore(String userQuery, String sessionId) {
        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(List.of(ChatMessage.UserMessage.of(userQuery)))
            .build();
        var response = client.chatCompletions().create(request).join();
        String sanitized = sanitize(response.firstContent());
        vectorStore.add(new Document(sanitized));
    }
}
