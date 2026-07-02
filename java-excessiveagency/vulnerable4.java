package main;

import dev.langchain4j.agent.tool.Tool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DatabaseTools {
    private final Connection conn;

    DatabaseTools(Connection conn) {
        this.conn = conn;
    }

    @Tool("Query the database to answer questions")
    public String queryDatabase(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append(rs.getString(1)).append("\n");
        }
        return sb.toString();
    }
}
