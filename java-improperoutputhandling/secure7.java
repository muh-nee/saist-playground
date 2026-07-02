import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class secure9 {
    static class FileOperation {
        public String action;
        public String path;
    }

    interface CommandService {
        FileOperation extractCommand(String description);
    }

    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private static final Set<String> ALLOWED_ACTIONS = Set.of("list", "read");
    private static final Path BASE_DIR = Paths.get("/app/data").toAbsolutePath().normalize();

    public void handleRequest(String description) throws Exception {
        CommandService svc = AiServices.create(CommandService.class, model);
        FileOperation op = svc.extractCommand(description);
        if (!ALLOWED_ACTIONS.contains(op.action)) {
            throw new IllegalArgumentException("Disallowed action");
        }
        Path resolved = BASE_DIR.resolve(op.path).normalize();
        if (!resolved.startsWith(BASE_DIR)) {
            throw new IllegalArgumentException("Path traversal detected");
        }
        new ProcessBuilder("ls", resolved.toString()).start();
    }
}
