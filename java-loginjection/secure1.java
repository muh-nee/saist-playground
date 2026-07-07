import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public void login(String username, String password) {
        // Safe: SLF4J {} placeholder binds username as a structured field, not into the message string
        logger.info("login_attempt for user: {}", username);
        authenticate(username, password);
    }

    private void authenticate(String username, String password) {
        // authentication logic
    }
}
