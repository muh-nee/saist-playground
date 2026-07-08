import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController {
    @GetMapping("/search", produces = ["text/html"])
    @ResponseBody
    fun search(@RequestParam q: String): String {
        return "<p>Results for: $q</p>"
    }
}
