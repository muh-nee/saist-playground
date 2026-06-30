import java.sql.Connection
import javax.servlet.http.HttpServletRequest

fun lookupUser(request: HttpServletRequest, conn: Connection) {
    val username = request.getParameter("username")
    val sql = "SELECT * FROM users WHERE name = '$username'"
    val stmt = conn.createStatement()
    stmt.executeQuery(sql)
}
