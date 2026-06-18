import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class vulnerable13 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();

    public void readFile(String description) throws Exception {
        String filename = model.generate("Return only the filename for: " + description).trim();
        new ProcessBuilder("cat", filename).start(); // sink: LLM-controlled argument — path/arg injection even without sh -c
    }
}
