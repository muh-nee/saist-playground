import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class ModelController {
    @PostMapping("/load")
    public String load(@RequestParam String modelUrl) throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(modelUrl)).build();
        byte[] modelBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        OrtSession session = env.createSession(modelBytes);
        return "loaded";
    }
}
