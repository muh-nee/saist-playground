import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class vulnerable3 {

    public void handleError(Exception e, HttpServletResponse response) throws IOException {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        response.setStatus(500);
        response.getWriter().write(sw.toString());
    }
}
