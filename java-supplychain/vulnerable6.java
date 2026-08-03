import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/model.onnx";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        String localPath = "/tmp/model.onnx";
        Files.copy(new URL(MODEL_URL).openStream(), Paths.get(localPath));
        return env.createSession(localPath);
    }
}
