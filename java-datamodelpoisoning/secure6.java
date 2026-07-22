package main;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Path;

@RestController
public class SafeModelController {
    private static final Path ALLOWED_DIR = Path.of("/opt/models").toAbsolutePath();

    @PostMapping("/load")
    public String load(@RequestParam String name) throws Exception {
        Path resolved = ALLOWED_DIR.resolve(name).normalize();
        if (!resolved.startsWith(ALLOWED_DIR)) return "forbidden";
        MultiLayerNetwork.load(resolved.toFile(), true);
        return "ok";
    }
}
