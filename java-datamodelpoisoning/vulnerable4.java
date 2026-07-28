package main;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnnxController {
    record LoadRequest(String modelPath) {}

    @PostMapping("/load")
    public String load(@RequestBody LoadRequest req) throws OrtException {
        OrtEnvironment.getEnvironment().createSession(req.modelPath());
        return "ok";
    }
}
