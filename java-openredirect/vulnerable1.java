import javax.servlet.http.*;
import java.io.IOException;

/**
 * Vulnerable: user-controlled request parameter passed directly to sendRedirect
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (authenticate(username, password)) {
            String redirectUrl = request.getParameter("redirect");
            // VULNERABLE: user-controlled redirect destination
            response.sendRedirect(redirectUrl);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "secret".equals(password);
    }
}
