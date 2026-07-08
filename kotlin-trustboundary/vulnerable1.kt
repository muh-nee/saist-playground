import javax.servlet.http.HttpServletRequest

fun storeUser(request: HttpServletRequest) {
    val userId = request.getParameter("userId")
    request.session.setAttribute("userId", userId)
}
