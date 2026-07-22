package main;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
public class InferController {
    private final OrtEnvironment env = OrtEnvironment.getEnvironment();
    private final OrtSession session = loadSession();

    @PostMapping("/infer")
    public String infer(@RequestBody float[][] features) throws OrtException {
        OnnxTensor input = OnnxTensor.createTensor(env, features);
        session.run(Collections.singletonMap("input", input));
        return "ok";
    }

    private static OrtSession loadSession() { return null; }
}
