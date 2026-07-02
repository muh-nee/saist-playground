package main;

import dev.langchain4j.agent.tool.Tool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class OrderTools {
    private final Connection conn;

    OrderTools(Connection conn) {
        this.conn = conn;
    }

    @Tool("Look up an order by ID")
    public String lookupOrder(String orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, status, total FROM orders WHERE id = ?");
        ps.setString(1, orderId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("id") + " " + rs.getString("status") + " " + rs.getString("total");
        }
        return "not found";
    }
}
