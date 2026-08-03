import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;

public class ModelLoader {
    private static final String MODEL_PATH = "./models/classifier.onnx";
    private static final OrtEnvironment ENV = OrtEnvironment.getEnvironment();

    public OrtSession load() throws Exception {
        return ENV.createSession(MODEL_PATH);
    }
}
