import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserEventLogger {
    private static final Logger logger = LoggerFactory.getLogger(UserEventLogger.class);

    public void logUserEvent(String username) {
        // Vulnerable: user input passed directly as the entire log message
        logger.info(username);
    }
}
