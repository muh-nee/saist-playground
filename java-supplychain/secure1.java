import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;

public class ModelLoader {
    private static final String MODEL_URL = "https://cdn.example.com/classifier.onnx";
    private static final String EXPECTED_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL)).build();
        byte[] modelBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(modelBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        if (!sb.toString().equals(EXPECTED_SHA256)) {
            throw new SecurityException("model integrity check failed");
        }
        return env.createSession(modelBytes);
    }
}
