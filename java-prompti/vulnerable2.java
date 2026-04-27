import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;

public class vulnerable2 {
    public static void main(String[] args) {
        String targetLanguage = args.length > 0 ? args[0] : "Spanish";
        String tone = args.length > 1 ? args[1] : "formal";
        String text = args.length > 2 ? args[2] : "Hello world";

        OpenAiService service = new OpenAiService("API_KEY");

        String systemPrompt = String.format(
            "You are a translation assistant. Translate to %s using a %s tone.",
            targetLanguage,
            tone
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                new ChatMessage("system", systemPrompt),
                new ChatMessage("user", text)
            ))
            .build();

        String result = service.createChatCompletion(request)
            .getChoices().get(0).getMessage().getContent();

        System.out.println(result);
    }
}
