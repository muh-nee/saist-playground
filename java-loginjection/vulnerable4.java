import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    public void createSession(String username) {
        // Vulnerable: String.format produces the full message string with user input embedded;
        // the resulting string is passed as the message argument, not as a placeholder value
        logger.info(String.format("User %s logged in", username));
    }
}
