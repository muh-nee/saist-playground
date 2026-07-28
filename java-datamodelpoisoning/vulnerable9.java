package main;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnnxPathController {
    @PostMapping("/load/{name}")
    public String load(@PathVariable String name) throws OrtException {
        String path = "/models/" + name;
        OrtEnvironment.getEnvironment().createSession(path);
        return "ok";
    }
}
