package main;

import dev.langchain4j.agent.tool.Tool;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class RefundTools {
    record RefundRequest(String orderId, int amountCents) {}

    private final Map<String, RefundRequest> pending = new ConcurrentHashMap<>();

    @Tool("Propose a refund — requires human approval before it is processed")
    public String requestRefund(String orderId, int amountCents) {
        String ticketId = "refund-" + orderId;
        pending.put(ticketId, new RefundRequest(orderId, amountCents));
        return ticketId;
    }

    public void approve(String ticketId, boolean humanApproved) {
        if (!humanApproved) throw new IllegalStateException("not approved");
        RefundRequest req = pending.remove(ticketId);
        if (req == null) throw new IllegalArgumentException("unknown ticket");
        // actual payment SDK call gated by human approval
    }
}
