import javax.servlet.http.HttpServletRequest

fun storeProfile(request: HttpServletRequest) {
    val raw = request.getParameter("profile")
    val processed = raw.trim().uppercase()
    request.session.putValue("profile", processed)
}
