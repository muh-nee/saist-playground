import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ModelLoader {
    private static final String MODEL_URL = "https://cdn.example.com/model.onnx";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL)).build();
        byte[] rawBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        byte[] modelBytes = rawBytes;
        return env.createSession(modelBytes, new OrtSession.SessionOptions());
    }
}
