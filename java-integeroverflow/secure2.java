import org.springframework.web.bind.annotation.*;

@RestController
public class SafeCastController {

    @GetMapping("/process")
    public String process(@RequestParam long value) {
        int id = Math.toIntExact(value); // throws ArithmeticException if value doesn't fit in int
        return String.valueOf(id);
    }
}
