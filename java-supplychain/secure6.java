import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class ModelService {
    private OrtSession session;

    @PostConstruct
    public void init() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        session = env.createSession("./models/production_classifier.onnx");
    }
}
