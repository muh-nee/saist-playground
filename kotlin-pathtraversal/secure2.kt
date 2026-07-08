import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import java.io.File

@RestController
@RequestMapping("/api")
class FileController {

    private val allowedFiles = setOf("readme.txt", "help.txt", "license.txt")
    private val baseDir = "/var/data"

    @GetMapping("/read")
    fun readFile(@RequestParam filename: String): ResponseEntity<String> {
        if (filename !in allowedFiles) {
            return ResponseEntity.status(403).body("File not allowed")
        }
        val content = File("$baseDir/$filename").readText()
        return ResponseEntity.ok(content)
    }
}
