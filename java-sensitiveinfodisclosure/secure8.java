import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import java.util.List;

public class secure8 {
    public String summarize(String documentText) {
        OpenAIClient client = new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(System.getenv("AZURE_OPENAI_API_KEY")))
                .endpoint(System.getenv("AZURE_OPENAI_ENDPOINT"))
                .buildClient();
        ChatCompletions completions = client.getChatCompletions(
                "gpt-4o-deployment",
                new ChatCompletionsOptions(List.of(
                        new ChatRequestUserMessage("Summarize: " + documentText)
                ))
        );
        return completions.getChoices().get(0).getMessage().getContent();
    }
}
