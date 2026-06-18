import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.yaml.snakeyaml.Yaml;

public class vulnerable10 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();

    public Object parseConfig(String description) {
        String yaml = model.generate("Generate a YAML config for: " + description);
        Yaml parser = new Yaml();
        return parser.load(yaml); // sink: unsafe deserialization of LLM output — gadget chains possible
    }
}
