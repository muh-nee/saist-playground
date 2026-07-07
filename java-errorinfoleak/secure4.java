import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppException extends RuntimeException {
    private final String userMessage;

    public AppException(String internalMessage, String userMessage) {
        super(internalMessage);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}

@RestControllerAdvice
class secure4 {
    private static final Logger logger = LoggerFactory.getLogger(secure4.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException e) {
        logger.error("App exception: {}", e.getMessage(), e);
        return ResponseEntity.status(500).body(e.getUserMessage());
    }
}
