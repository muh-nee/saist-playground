import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completioncreateparams.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import java.util.Set;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DependencyController {
    private static final Set<String> APPROVED_ARTIFACTS = Set.of(
        "ai.onnxruntime:onnxruntime:1.19.0",
        "ai.djl:api:0.28.0",
        "org.tensorflow:tensorflow-core-platform:0.5.0"
    );

    @PostMapping("/install-dep")
    public String installDep(@RequestParam String task) throws Exception {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .addUserMessage("What Maven artifact for: " + task + "? Reply with only groupId:artifactId:version.")
            .build();
        String artifact = client.chat().completions().create(params)
            .choices().get(0).message().content().orElse("").trim();
        if (!APPROVED_ARTIFACTS.contains(artifact)) {
            return "artifact not approved";
        }
        new ProcessBuilder("mvn", "dependency:get", "-Dartifact=" + artifact).start().waitFor();
        return "installed: " + artifact;
    }
}
