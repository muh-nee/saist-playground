// Vulnerable: Spring AI output passed to Runtime.getRuntime().exec()
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

public class vulnerable2 {
    private final OpenAiChatModel chatModel;

    public vulnerable2(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public void runTask(String taskDescription) throws Exception {
        ChatResponse response = chatModel.call(new Prompt(taskDescription));
        String command = response.getResult().getOutput().getContent();
        Runtime.getRuntime().exec(new String[]{"sh", "-c", command}); // sink: raw LLM output in shell
    }
}
