import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document

fun lookupEmail(doc: Document, user: String): String {
    val xpath = XPathFactory.newInstance().newXPath()
    val expr = "/users/user[name='$user']/email/text()"
    return xpath.evaluate(expr, doc)
}
