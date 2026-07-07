import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {

    private static final int[] DATA = new int[10000];

    @GetMapping("/read")
    public int read(@RequestParam int index, @RequestParam int base) {
        return DATA[base + index]; // base + index may overflow, producing negative index
    }
}
