import java.io.File
import javax.servlet.http.HttpServletRequest

fun download(request: HttpServletRequest): ByteArray {
    val name = request.getParameter("file")
    val file = File("/var/www/files/$name")
    return file.readBytes()
}
