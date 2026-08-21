import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completioncreateparams.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetupController {
    @PostMapping("/setup-deps")
    public String setupDeps(@RequestParam String feature) throws Exception {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .addUserMessage("List Maven artifacts for: " + feature + ". One groupId:artifactId:version per line.")
            .build();
        String response = client.chat().completions().create(params)
            .choices().get(0).message().content().orElse("").trim();
        for (String artifact : response.split("\n")) {
            new ProcessBuilder("mvn", "dependency:get", "-Dartifact=" + artifact.trim()).start().waitFor();
        }
        return "done";
    }
}
