package main;

import dev.langchain4j.agent.tool.Tool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class OrderCleanupTools {
    private final Connection conn;

    OrderCleanupTools(Connection conn) {
        this.conn = conn;
    }

    @Tool("Delete an order by ID")
    public String deleteOrder(String orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM orders WHERE id = ?");
        ps.setString(1, orderId);
        int rows = ps.executeUpdate();
        return "deleted " + rows;
    }
}
