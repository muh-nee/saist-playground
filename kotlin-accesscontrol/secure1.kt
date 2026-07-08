import java.security.Principal
import java.util.Optional
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class Document(val id: Long, val owner: String, val body: String)
interface DocumentRepository { fun findById(id: Long): Optional<Document> }

@RestController
class DocumentController(private val documentRepository: DocumentRepository) {
    @GetMapping("/documents/{id}")
    fun getDocument(@PathVariable id: Long, principal: Principal): Document {
        val doc = documentRepository.findById(id).orElseThrow()
        if (doc.owner != principal.name) throw AccessDeniedException("Not authorized")
        return doc
    }
}
