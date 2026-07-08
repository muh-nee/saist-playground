import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

fun rememberMe(response: HttpServletResponse, token: String) {
    val cookie = Cookie("rememberMe", token)
    cookie.isHttpOnly = true
    cookie.maxAge = 3600
    response.addCookie(cookie)
}
