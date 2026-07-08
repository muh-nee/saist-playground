import javax.servlet.http.HttpServletRequest

fun ping(request: HttpServletRequest) {
    val host = request.getParameter("host")
    Runtime.getRuntime().exec("ping -c 1 $host")
}
