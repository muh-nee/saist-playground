import org.springframework.web.bind.annotation.*;

@RestController
public class SafePaginationController {

    @GetMapping("/items")
    public String getItems(@RequestParam int page, @RequestParam int pageSize) {
        int offset = Math.addExact(Math.multiplyExact(page, pageSize), 0); // checked arithmetic
        return fetchItems(offset);
    }

    private String fetchItems(int offset) {
        return "offset=" + offset;
    }
}
