import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

// Safe: allowlist of permitted redirect destinations
@Controller
class SafeAuthController {
    companion object {
        private val ALLOWED_REDIRECTS = setOf("/dashboard", "/profile", "/settings", "/home")
    }

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam(defaultValue = "/dashboard") returnTo: String
    ): String {
        if (authenticate(username, password)) {
            // Safe: only redirect to explicitly allowed destinations
            val safeReturnTo = if (returnTo in ALLOWED_REDIRECTS) returnTo else "/dashboard"
            return "redirect:$safeReturnTo"
        }
        return "redirect:/login?error=1"
    }

    private fun authenticate(username: String, password: String): Boolean {
        return username == "admin" && password == "secret"
    }
}
