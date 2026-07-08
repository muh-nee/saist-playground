import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/api")
class FileController {

    @GetMapping("/read")
    fun readFile(@RequestParam filename: String): ResponseEntity<String> {
        val content = FileInputStream("/var/data/$filename").bufferedReader().readText()
        return ResponseEntity.ok(content)
    }

    @GetMapping("/fetch/{name}")
    fun fetchFile(@PathVariable name: String): ResponseEntity<ByteArray> {
        val bytes = Files.readAllBytes(Paths.get("/uploads", name))
        return ResponseEntity.ok(bytes)
    }
}
