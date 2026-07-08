import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Set;
import java.io.*;
import java.nio.file.*;

@RestController
public class DocumentController {

    private static final Set<String> ALLOWED = Set.of("manual.pdf", "readme.txt");
    private static final String BASE = "/uploads";

    @GetMapping("/document")
    public ResponseEntity<byte[]> getDocument(@RequestParam String name) throws Exception {
        if (!ALLOWED.contains(name)) {
            return ResponseEntity.status(403).build();
        }
        byte[] bytes = Files.readAllBytes(Paths.get(BASE, name));
        return ResponseEntity.ok(bytes);
    }
}
