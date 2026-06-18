import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;

import java.util.Set;

public class secure8 {
    private final OpenAiChatModel chatModel;

    public secure8(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    static class FileOperation {
        public String action; // constrained to list/read
        public String path;
    }

    private static final Set<String> ALLOWED_ACTIONS = Set.of("list", "read");

    public void handleOp(String prompt) throws Exception {
        BeanOutputConverter<FileOperation> converter = new BeanOutputConverter<>(FileOperation.class);
        String content = ChatClient.create(chatModel).prompt().user(prompt).call().content();
        FileOperation op = converter.convert(content);
        if (!ALLOWED_ACTIONS.contains(op.action)) {
            throw new IllegalArgumentException("Disallowed action");
        }
        new ProcessBuilder("ls", op.path).start();
    }
}
