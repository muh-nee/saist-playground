import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class vulnerable8 {
	private final OpenAiChatModel chatModel;
	private final Configuration cfg;

	public vulnerable8(OpenAiChatModel chatModel, Configuration cfg) {
		this.chatModel = chatModel;
		this.cfg = cfg;
	}

	public String renderSummary(String topic) throws Exception {
		ChatResponse response = chatModel.call(new Prompt("Write a summary about: " + topic));
		String llmContent = response.getResult().getOutput().getContent();
		Template template = new Template("summary", new StringReader("<div>${summary}</div>"), cfg);
		StringWriter writer = new StringWriter();
		template.process(Map.of("summary", llmContent), writer);
		return writer.toString();
	}
}
