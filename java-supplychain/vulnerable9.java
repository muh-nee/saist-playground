import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ModelLoader {
    public OrtSession loadFromEnv() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        String modelUrl = System.getenv("MODEL_DOWNLOAD_URL");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(modelUrl)).build();
        byte[] modelBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        return env.createSession(modelBytes);
    }
}
