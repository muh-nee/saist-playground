import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/classifier.onnx";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        HttpURLConnection conn = (HttpURLConnection) new URL(MODEL_URL).openConnection();
        byte[] modelBytes = conn.getInputStream().readAllBytes();
        return env.createSession(modelBytes);
    }
}
