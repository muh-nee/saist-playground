import javax.servlet.http.HttpServletRequest

fun cacheLocale(request: HttpServletRequest) {
    val locale = request.getHeader("Accept-Language")
    request.getSession(true).setAttribute("locale", locale)
}
