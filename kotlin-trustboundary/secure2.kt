import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder

fun bindIdentity(request: HttpServletRequest) {
    val username = SecurityContextHolder.getContext().authentication.name
    request.session.setAttribute("username", username)
}
