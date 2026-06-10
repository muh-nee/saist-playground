// Safe: langchain4j @AiService interface returns a typed POJO — framework parses JSON into declared type
import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Set;

public class secure9 {
    static class FileOperation {
        public String action; // "list" or "read"
        public String path;
    }

    interface CommandService {
        FileOperation extractCommand(String description); // returns typed POJO, not String
    }

    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private static final Set<String> ALLOWED_ACTIONS = Set.of("list", "read");

    public void handleRequest(String description) throws Exception {
        CommandService svc = AiServices.create(CommandService.class, model);
        FileOperation op = svc.extractCommand(description); // safe: structure constrained by FileOperation schema
        if (!ALLOWED_ACTIONS.contains(op.action)) {
            throw new IllegalArgumentException("Disallowed action");
        }
        new ProcessBuilder("ls", op.path).start();
    }
}
