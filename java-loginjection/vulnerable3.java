import org.apache.log4j.Logger;

public class OrderService {
    private static final Logger LOG = Logger.getLogger(OrderService.class);

    public void processOrder(String orderId) {
        try {
            placeOrder(orderId);
        } catch (Exception e) {
            // Vulnerable: user-supplied orderId concatenated into log4j message
            LOG.error("Failed order: " + orderId);
        }
    }

    private void placeOrder(String orderId) throws Exception {
        // order logic
    }
}
