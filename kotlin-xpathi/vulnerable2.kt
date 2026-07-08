import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.NodeList

fun authenticate(doc: Document, user: String, pass: String): Boolean {
    val xpath = XPathFactory.newInstance().newXPath()
    val expr = xpath.compile("//user[name='$user' and pass='$pass']")
    val nodes = expr.evaluate(doc, XPathConstants.NODESET) as NodeList
    return nodes.length > 0
}
