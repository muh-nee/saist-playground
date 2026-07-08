import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document

fun lookupById(doc: Document, id: String): String {
    require(id.matches(Regex("\\d+"))) { "invalid id" }
    val xpath = XPathFactory.newInstance().newXPath()
    return xpath.evaluate("//item[@id='$id']", doc)
}
