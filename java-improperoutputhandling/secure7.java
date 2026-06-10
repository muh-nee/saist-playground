// Safe: Spring AI output placed in Thymeleaf model using th:text (auto-escapes) instead of th:utext
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ui.Model;

public class secure7 {
    private final OpenAiChatModel chatModel;

    public secure7(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // Thymeleaf template uses th:text="${summary}" — auto-escapes HTML special characters
    // safe: th:text escapes output; th:utext would be the vulnerable variant
    public String renderPage(String topic, Model model) {
        ChatResponse response = chatModel.call(new Prompt("Write a summary about: " + topic));
        String summary = response.getResult().getOutput().getContent();
        model.addAttribute("summary", summary); // safe: Thymeleaf th:text auto-escapes this value
        return "summary"; // resolves to summary.html — uses th:text, not th:utext
    }
}
