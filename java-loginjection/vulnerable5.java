import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    public void processPayment(String userId) {
        try {
            charge(userId);
        } catch (Exception e) {
            // Vulnerable: userId and exception message both concatenated into the log message string
            log.error("Exception processing user: " + userId + " - " + e.getMessage());
        }
    }

    private void charge(String userId) throws Exception {
        // payment logic
    }
}
