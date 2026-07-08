import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

/**
 * Safe: parse URI and verify no authority before redirecting
 */
@Controller
public class SafeAuthController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(defaultValue = "/dashboard") String next) {
        if (authenticate(username, password)) {
            // Safe: verify the URI is relative (no scheme, no authority)
            try {
                URI uri = URI.create(next);
                if (uri.isAbsolute() || uri.getAuthority() != null) {
                    next = "/dashboard";
                }
            } catch (IllegalArgumentException e) {
                next = "/dashboard";
            }
            return "redirect:" + next;
        }
        return "login";
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "secret".equals(password);
    }
}
