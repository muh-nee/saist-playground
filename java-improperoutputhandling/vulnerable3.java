// Vulnerable: OpenAI Java SDK output evaluated as script code via ScriptEngine (Nashorn)
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class vulnerable3 {
    private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    public Object executeScript(String task) throws Exception {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addUserMessage("Write a JavaScript snippet to: " + task)
                .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String code = completion.choices().get(0).message().content().orElse("");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        return engine.eval(code); // sink: direct code execution of LLM output
    }
}
