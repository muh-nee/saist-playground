import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class vulnerable4 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private final Configuration cfg;

    public vulnerable4(Configuration cfg) {
        this.cfg = cfg;
    }

    public void renderPage(String topic, Writer writer) throws Exception {
        String llmContent = model.generate("Write a short summary about: " + topic);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("content", llmContent);
        Template template = cfg.getTemplate("page.ftl"); // template uses ${content}
        template.process(dataModel, writer); // sink: FreeMarker renders unescaped LLM output — XSS
    }
}
