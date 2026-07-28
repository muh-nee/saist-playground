package main;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
public class ModelController {
    @PostMapping("/load")
    public String load(@RequestParam String modelPath) throws Exception {
        MultiLayerNetwork.load(new File(modelPath), true);
        return "ok";
    }
}
