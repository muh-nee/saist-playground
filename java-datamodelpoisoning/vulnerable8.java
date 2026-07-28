package main;

import ai.onnxruntime.OrtEnvironment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OnnxUploadController {
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        OrtEnvironment.getEnvironment().createSession(file.getBytes());
        return "ok";
    }
}
