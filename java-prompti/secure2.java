import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;

public class secure2 {
    private static final String SYSTEM_PROMPT = "You are a data analysis assistant. Analyze the provided metrics.";

    public static void main(String[] args) {
        String contextName = args.length > 0 ? args[0] : "checkout";
        String startTime = args.length > 1 ? args[1] : "2024-01-01";
        String endTime = args.length > 2 ? args[2] : "2024-01-31";
        String userMessage = args.length > 3 ? args[3] : "";

        OpenAiService service = new OpenAiService("API_KEY");

        String userPrompt = String.format("Context: %s\nTime range: %s to %s", contextName, startTime, endTime);
        if (userMessage != null && !userMessage.isEmpty()) {
            userPrompt += "\n\n" + userMessage;
        }

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                new ChatMessage("system", SYSTEM_PROMPT),
                new ChatMessage("user", userPrompt)
            ))
            .build();

        String result = service.createChatCompletion(request)
            .getChoices().get(0).getMessage().getContent();

        System.out.println(result);
    }
}
