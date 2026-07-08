import java.io.StringReader
import javax.servlet.http.HttpServletRequest
import javax.xml.xpath.XPathFactory
import org.xml.sax.InputSource

fun query(request: HttpServletRequest, xml: String): String {
    val id = request.getParameter("id")
    val xpath = XPathFactory.newInstance().newXPath()
    val expr = "//item[@id='$id']"
    return xpath.evaluate(expr, InputSource(StringReader(xml)))
}
