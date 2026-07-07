import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public void login(String username, String password) {
        // Vulnerable: user-supplied username concatenated directly into log message
        logger.info("Login attempt for user: " + username);
        authenticate(username, password);
    }

    private void authenticate(String username, String password) {
        // authentication logic
    }
}
