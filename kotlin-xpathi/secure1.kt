import javax.xml.xpath.XPathFactory
import javax.xml.xpath.XPathVariableResolver
import org.w3c.dom.Document

fun lookupEmail(doc: Document, user: String): String {
    val xpath = XPathFactory.newInstance().newXPath()
    xpath.xPathVariableResolver = XPathVariableResolver { name ->
        if (name.localPart == "user") user else null
    }
    return xpath.evaluate("/users/user[name=\$user]/email/text()", doc)
}
