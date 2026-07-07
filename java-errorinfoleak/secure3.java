import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class secure3 {
    private static final Logger logger = LoggerFactory.getLogger(secure3.class);

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        logger.error("Unhandled exception", e);
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
    }
}
