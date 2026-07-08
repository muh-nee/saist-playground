import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;

public class FileServlet extends HttpServlet {
    private static final String BASE = "/var/data";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = request.getParameter("file");
        Path base = Paths.get(BASE).toRealPath();
        Path target = base.resolve(filename).normalize();
        if (!target.startsWith(base)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid path");
            return;
        }
        response.getOutputStream().write(Files.readAllBytes(target));
    }
}
