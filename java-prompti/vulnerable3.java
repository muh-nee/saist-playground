import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;

public class vulnerable3 {
    public static void main(String[] args) {
        String userName = args.length > 0 ? args[0] : "Alice";
        String department = args.length > 1 ? args[1] : "Engineering";
        String userMessage = args.length > 2 ? args[2] : "Hello";

        OpenAiService service = new OpenAiService("API_KEY");

        String systemPrompt = "You are an AI assistant.";
        systemPrompt += " You are helping " + userName;
        systemPrompt += " from the " + department + " department.";

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                new ChatMessage("system", systemPrompt),
                new ChatMessage("user", userMessage)
            ))
            .build();

        String result = service.createChatCompletion(request)
            .getChoices().get(0).getMessage().getContent();

        System.out.println(result);
    }
}
