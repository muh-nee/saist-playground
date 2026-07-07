import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void handleUser(String userId) {
        // Safe: user data stored in MDC context; the log message itself is a fixed string
        MDC.put("userId", userId);
        try {
            logger.info("login_attempt");
            processUser(userId);
        } finally {
            MDC.remove("userId");
        }
    }

    private void processUser(String userId) {
        // user processing logic
    }
}
