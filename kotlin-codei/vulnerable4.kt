import javax.servlet.http.HttpServletRequest

fun instantiate(request: HttpServletRequest): Any {
    val className = request.getParameter("class")
    val clazz = Class.forName(className)
    return clazz.getDeclaredConstructor().newInstance()
}
