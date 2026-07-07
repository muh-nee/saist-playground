import org.springframework.web.bind.annotation.*;

@RestController
public class PaginationController {

    @GetMapping("/items")
    public String getItems(@RequestParam int page, @RequestParam int pageSize) {
        int offset = page * pageSize; // may overflow if page and pageSize are large
        return fetchItems(offset);
    }

    private String fetchItems(int offset) {
        return "offset=" + offset;
    }
}
