import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class vulnerable2 {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.status(500)
            .body(Map.of("error", e.getMessage()));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<String> getData(@PathVariable String id) {
        try {
            return ResponseEntity.ok(fetchData(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    private String fetchData(String id) throws Exception {
        return "data";
    }
}
