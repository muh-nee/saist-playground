import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void processOrder(String userId, String orderId) {
        // Safe: multiple SLF4J {} placeholders — both values are structured arguments
        logger.info("order_processed for user: {} with orderId: {}", userId, orderId);
        fulfill(userId, orderId);
    }

    private void fulfill(String userId, String orderId) {
        // fulfillment logic
    }
}
