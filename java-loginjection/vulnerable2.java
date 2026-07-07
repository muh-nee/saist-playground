import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

public class RequestLogger {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogger.class);

    public void logRequest(HttpServletRequest request) {
        // Vulnerable: HTTP header value concatenated into log message string
        logger.warn("Request from: " + request.getHeader("X-Forwarded-For"));
    }
}
