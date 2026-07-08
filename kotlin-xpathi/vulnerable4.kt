import io.ktor.server.application.ApplicationCall
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document

fun ApplicationCall.role(doc: Document): String {
    val name = parameters["name"] ?: ""
    val xpath = XPathFactory.newInstance().newXPath()
    return xpath.evaluate("//account[username='$name']/role", doc)
}
