import java.sql.Connection

fun lookupUser(conn: Connection, username: String) {
    val sql = "SELECT * FROM users WHERE name = ?"
    conn.prepareStatement(sql).use { ps ->
        ps.setString(1, username)
        ps.executeQuery()
    }
}
