import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun render(request: HttpServletRequest, response: HttpServletResponse) {
    val template = request.getHeader("X-Template")
    response.contentType = "text/html"
    response.writer.format(template, "a", "b")
}
