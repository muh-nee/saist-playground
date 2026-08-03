import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.web.client.RestTemplate;
import java.security.MessageDigest;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/classifier.onnx";
    private static final String EXPECTED_SHA256 = "c3ab8ff13720e8ad9047dd39466b3c8974e592c2fa383d4a3960714caef0c4f2";
    private final RestTemplate restTemplate = new RestTemplate();

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        byte[] modelBytes = restTemplate.getForObject(MODEL_URL, byte[].class);
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(modelBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        if (!sb.toString().equals(EXPECTED_SHA256)) {
            throw new SecurityException("integrity check failed");
        }
        return env.createSession(modelBytes);
    }
}
