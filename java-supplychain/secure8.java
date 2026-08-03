import org.tensorflow.SavedModelBundle;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TfInferenceController {
    private static final String MODEL_DIR = "./models/saved_model";

    @PostMapping("/infer")
    public String infer(@RequestParam String input) {
        SavedModelBundle bundle = SavedModelBundle.load(MODEL_DIR, "serve");
        return "inferred";
    }
}
