import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OnnxTensor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class InferenceController {
    private static final OrtEnvironment ENV = OrtEnvironment.getEnvironment();
    private OrtSession session;

    public InferenceController() throws Exception {
        session = ENV.createSession("./models/classifier.onnx");
    }

    @PostMapping("/infer")
    public String infer(@RequestParam float[] features) throws Exception {
        OnnxTensor tensor = OnnxTensor.createTensor(ENV, features);
        OrtSession.Result result = session.run(Map.of("input", tensor));
        return result.toString();
    }
}
