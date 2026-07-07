import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class secure1 {
    private static final Logger logger = LoggerFactory.getLogger(secure1.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        logger.error("Unhandled exception", e);
        return ResponseEntity.status(500)
            .body(Map.of("error", "internal server error"));
    }
}
