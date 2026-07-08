import javax.servlet.http.*;
import java.io.IOException;
import java.util.Set;

/**
 * Safe: allowlist of permitted redirect destinations
 */
@SuppressWarnings("serial")
public class SafeLoginServlet extends HttpServlet {
    private static final Set<String> ALLOWED_REDIRECTS =
        Set.of("/dashboard", "/profile", "/settings", "/home");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (authenticate(username, password)) {
            String redirectUrl = request.getParameter("redirect");
            // Safe: only redirect to explicitly allowed destinations
            if (!ALLOWED_REDIRECTS.contains(redirectUrl)) {
                redirectUrl = "/dashboard";
            }
            response.sendRedirect(redirectUrl);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "secret".equals(password);
    }
}
