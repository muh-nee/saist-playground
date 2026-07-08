import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.nio.file.*;

@RestController
public class DocumentController {

    @GetMapping("/document")
    public ResponseEntity<byte[]> getDocument(@RequestParam String name,
                                              @RequestHeader("X-Folder") String folder) throws Exception {
        Path path = Paths.get("/uploads", folder, name);
        byte[] bytes = Files.readAllBytes(path);
        return ResponseEntity.ok(bytes);
    }
}
