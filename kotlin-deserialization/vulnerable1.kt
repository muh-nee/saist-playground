import java.io.ObjectInputStream
import javax.servlet.http.HttpServletRequest

fun load(request: HttpServletRequest): Any {
    val ois = ObjectInputStream(request.inputStream)
    return ois.readObject()
}
