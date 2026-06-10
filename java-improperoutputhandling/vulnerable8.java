// Vulnerable: Spring AI output placed into Velocity template context — rendered unescaped
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import java.io.StringWriter;

public class vulnerable8 {
    private final OpenAiChatModel chatModel;
    private final VelocityEngine velocityEngine;

    public vulnerable8(OpenAiChatModel chatModel, VelocityEngine velocityEngine) {
        this.chatModel = chatModel;
        this.velocityEngine = velocityEngine;
    }

    public String renderSummary(String topic) {
        ChatResponse response = chatModel.call(new Prompt("Write a summary about: " + topic));
        String llmContent = response.getResult().getOutput().getContent();
        VelocityContext context = new VelocityContext();
        context.put("summary", llmContent);
        Template template = velocityEngine.getTemplate("summary.vm"); // template uses $summary — no escaping
        StringWriter writer = new StringWriter();
        template.merge(context, writer); // sink: Velocity renders LLM output unescaped — XSS if HTML context
        return writer.toString();
    }
}
