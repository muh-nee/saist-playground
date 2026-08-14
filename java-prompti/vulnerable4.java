import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.util.List;

public class MemoryService {

    private final SimpleOpenAI client;
    private final EmbeddingStore<Document> vectorStore;

    public MemoryService(SimpleOpenAI client, EmbeddingStore<Document> vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }

    public void summarizeAndStore(String userQuery, String sessionId) {
        var request = ChatRequest.builder()
            .model("gpt-4o")
            .messages(List.of(ChatMessage.UserMessage.of(userQuery)))
            .build();
        var response = client.chatCompletions().create(request).join();
        String llmOutput = response.firstContent();
        vectorStore.add(new Document(llmOutput));
    }
}
