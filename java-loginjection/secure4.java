import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public void login(String username, String password) {
        // Safe: CRLF characters stripped before the value is concatenated into the log message,
        // preventing newline injection that could forge additional log entries
        String sanitized = username.replaceAll("[\\r\\n]", "");
        logger.info("Login attempt for user: " + sanitized);
        authenticate(sanitized, password);
    }

    private void authenticate(String username, String password) {
        // authentication logic
    }
}
