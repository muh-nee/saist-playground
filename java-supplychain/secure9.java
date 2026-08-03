import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/model.onnx";
    private static final String EXPECTED_SHA256 = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        String localPath = "/tmp/model.onnx";
        Files.copy(new URL(MODEL_URL).openStream(), Paths.get(localPath));
        byte[] fileBytes = Files.readAllBytes(Paths.get(localPath));
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        if (!sb.toString().equals(EXPECTED_SHA256)) {
            throw new SecurityException("integrity check failed");
        }
        return env.createSession(localPath);
    }
}
