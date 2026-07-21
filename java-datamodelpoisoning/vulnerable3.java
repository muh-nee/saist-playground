package main;

import org.deeplearning4j.util.ModelSerializer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
public class RestoreController {
    @PostMapping("/restore")
    public String restore(@RequestParam String modelPath) throws Exception {
        ModelSerializer.restoreMultiLayerNetwork(new File(modelPath), true);
        return "ok";
    }
}
