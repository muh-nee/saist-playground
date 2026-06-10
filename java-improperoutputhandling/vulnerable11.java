// Vulnerable: OpenAI Java SDK output used as open redirect target via response.sendRedirect()
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import javax.servlet.http.HttpServletResponse;

public class vulnerable11 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public void redirect(String userRequest, HttpServletResponse response) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Return only the redirect URL for: " + userRequest)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String target = completion.choices().get(0).message().content().orElse("").trim();
        response.sendRedirect(target); // sink: LLM-controlled redirect target — open redirect
    }
}
