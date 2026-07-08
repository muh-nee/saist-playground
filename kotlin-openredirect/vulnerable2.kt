import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

// Vulnerable: Spring Kotlin controller with user-controlled redirect URL
@Controller
class AuthController {

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam(defaultValue = "/") returnTo: String
    ): String {
        if (authenticate(username, password)) {
            // VULNERABLE: //evil.com/path would pass a startsWith("/") check
            // and gets injected into the redirect string
            return "redirect:$returnTo"
        }
        return "redirect:/login?error=1"
    }

    private fun authenticate(username: String, password: String): Boolean {
        return username == "admin" && password == "secret"
    }
}
