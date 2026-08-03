import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/model.onnx";
    private static final String EXPECTED_SHA256 = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (DigestInputStream dis = new DigestInputStream(new URL(MODEL_URL).openStream(), md)) {
            byte[] modelBytes = dis.readAllBytes();
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            if (!sb.toString().equals(EXPECTED_SHA256)) {
                throw new SecurityException("integrity check failed");
            }
            return env.createSession(modelBytes);
        }
    }
}
