import javax.servlet.http.HttpServletRequest

fun initSession(request: HttpServletRequest) {
    request.session.setAttribute("role", "GUEST")
    request.session.setAttribute("createdAt", System.currentTimeMillis())
}
