import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText
import java.sql.Connection

suspend fun ApplicationCall.deleteAccount(conn: Connection) {
    val id = parameters["id"] ?: ""
    val sql = "DELETE FROM accounts WHERE id = $id"
    conn.createStatement().executeUpdate(sql)
    respondText("deleted")
}
