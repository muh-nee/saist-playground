import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

fun setUserCookie(response: HttpServletResponse, value: String) {
    val cookie = Cookie("user", value)
    cookie.secure = true
    cookie.isHttpOnly = true
    response.addCookie(cookie)
}
