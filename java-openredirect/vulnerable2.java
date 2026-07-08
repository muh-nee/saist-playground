import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Vulnerable: Spring MVC return redirect with user-supplied URL
 */
@Controller
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(defaultValue = "/") String next) {
        if (authenticate(username, password)) {
            // VULNERABLE: user-controlled URL concatenated into redirect
            return "redirect:" + next;
        }
        return "login";
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "secret".equals(password);
    }
}
