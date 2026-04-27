import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;

public class vulnerable1 {
    public static void main(String[] args) {
        String userRole = args.length > 0 ? args[0] : "helpful";
        String message = args.length > 1 ? args[1] : "Hello";

        OpenAiService service = new OpenAiService("API_KEY");

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                new ChatMessage("system", "You are a " + userRole + " assistant."),
                new ChatMessage("user", message)
            ))
            .build();

        String result = service.createChatCompletion(request)
            .getChoices().get(0).getMessage().getContent();

        System.out.println(result);
    }
}
