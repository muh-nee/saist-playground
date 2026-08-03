import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.web.client.RestTemplate;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/classifier.onnx";
    private final RestTemplate restTemplate = new RestTemplate();

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        byte[] modelBytes = restTemplate.getForObject(MODEL_URL, byte[].class);
        return env.createSession(modelBytes);
    }
}
