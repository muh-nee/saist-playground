package main;

import weka.core.SerializationHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class WekaController {
    private static final Map<String, String> MODELS = Map.of(
        "spam", "./models/spam.model",
        "sentiment", "./models/sentiment.model"
    );

    @PostMapping("/load/{name}")
    public String load(@PathVariable String name) throws Exception {
        String path = MODELS.get(name);
        if (path == null) return "not found";
        SerializationHelper.read(path);
        return "ok";
    }
}
