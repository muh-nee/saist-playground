import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class vulnerable6 {
    private final ChatClient chatClient;

    public vulnerable6(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String answer(String userMessage) {
        return chatClient.prompt(userMessage)
            .options(OpenAiChatOptions.builder()
                .model("gpt-4o")
                .build())
            .call()
            .content();
    }
}
