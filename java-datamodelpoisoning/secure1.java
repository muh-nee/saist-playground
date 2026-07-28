package main;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.util.Set;

@RestController
public class ModelController {
    private static final Set<String> APPROVED = Set.of("v1.bin", "v2.bin", "latest.bin");

    @PostMapping("/load")
    public String load(@RequestParam String name) throws Exception {
        if (!APPROVED.contains(name)) return "forbidden";
        MultiLayerNetwork.load(new File("./models/" + name), true);
        return "ok";
    }
}
