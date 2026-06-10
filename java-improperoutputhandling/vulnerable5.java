// Vulnerable: OpenAI Java SDK output written directly to HTML response
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class vulnerable5 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public void handleRequest(String topic, HttpServletResponse resp) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage(topic)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String llmContent = completion.choices().get(0).message().content().orElse("");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<div>" + llmContent + "</div>"); // sink: raw LLM output in HTML response — XSS
    }
}
