package main;

import dev.langchain4j.agent.tool.Tool;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

class OrderCleanupTools {
    private final Connection conn;

    OrderCleanupTools(Connection conn) {
        this.conn = conn;
    }

    @Tool("Delete an order by ID")
    public String deleteOrder(String orderId) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM orders WHERE id = " + orderId);
        return "deleted";
    }
}
