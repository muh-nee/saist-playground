// Vulnerable: Spring AI output used as JNDI lookup name (Log4Shell-class sink)
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import javax.naming.Context;
import javax.naming.InitialContext;

public class vulnerable2 {
    private final OpenAiChatModel chatModel;

    public vulnerable2(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Object resolveResource(String description) throws Exception {
        ChatResponse response = chatModel.call(new Prompt("Return only the JNDI name for: " + description));
        String name = response.getResult().getOutput().getContent().trim();
        Context ctx = new InitialContext();
        return ctx.lookup(name); // sink: LLM-controlled JNDI name — remote class loading / RCE
    }
}
