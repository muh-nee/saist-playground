import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;

public class secure1 {
    private static final String SYSTEM_PROMPT = "You are a helpful customer support assistant.";

    public static void main(String[] args) {
        String message = args.length > 0 ? args[0] : "Hello";

        OpenAiService service = new OpenAiService("API_KEY");

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                new ChatMessage("system", SYSTEM_PROMPT),
                new ChatMessage("user", message)
            ))
            .build();

        String result = service.createChatCompletion(request)
            .getChoices().get(0).getMessage().getContent();

        System.out.println(result);
    }
}
