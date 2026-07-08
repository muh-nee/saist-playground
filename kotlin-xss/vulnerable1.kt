import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun greet(request: HttpServletRequest, response: HttpServletResponse) {
    val name = request.getParameter("name")
    response.contentType = "text/html"
    response.writer.println("<h2>Welcome, $name</h2>")
}
