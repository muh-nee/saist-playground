package main;

import weka.core.SerializationHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WekaController {
    @PostMapping("/load")
    public String load(@RequestParam String modelPath) throws Exception {
        SerializationHelper.read(modelPath);
        return "ok";
    }
}
