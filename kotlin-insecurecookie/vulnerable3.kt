import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

fun sessionCookie(response: HttpServletResponse, sessionId: String) {
    val cookie = Cookie("SESSIONID", sessionId)
    cookie.secure = false
    response.addCookie(cookie)
}
