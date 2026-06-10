// Safe: OpenAI Java SDK output loaded by SnakeYAML with SafeConstructor — prevents gadget chain deserialization
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class secure11 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public Object parseConfig(String description) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Generate a simple YAML config for: " + description)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String yaml = completion.choices().get(0).message().content().orElse("");
        Yaml parser = new Yaml(new SafeConstructor(new LoaderOptions())); // safe: SafeConstructor prevents arbitrary class instantiation
        return parser.load(yaml);
    }
}
