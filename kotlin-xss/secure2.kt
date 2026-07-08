import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {
    @GetMapping("/greet")
    fun greet(@RequestParam name: String, model: Model): String {
        model.addAttribute("name", name)
        return "greeting"
    }
}
