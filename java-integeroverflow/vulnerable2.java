import org.springframework.web.bind.annotation.*;

@RestController
public class CastController {

    @GetMapping("/process")
    public String process(@RequestParam long value) {
        int id = (int) value; // silently truncates if value > Integer.MAX_VALUE
        return String.valueOf(id);
    }
}
