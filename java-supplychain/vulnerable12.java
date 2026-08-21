import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completioncreateparams.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DependencyController {
    @PostMapping("/install-dep")
    public String installDep(@RequestParam String task) throws Exception {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .addUserMessage("What Maven artifact should I use for: " + task + "? Reply with only groupId:artifactId:version.")
            .build();
        String artifact = client.chat().completions().create(params)
            .choices().get(0).message().content().orElse("").trim();
        Runtime.getRuntime().exec("mvn dependency:get -Dartifact=" + artifact);
        return "installing: " + artifact;
    }
}
