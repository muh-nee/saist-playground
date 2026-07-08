import javax.servlet.http.*;
import java.io.*;

public class FileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = request.getParameter("file");
        FileInputStream fis = new FileInputStream("/var/data/" + filename);
        byte[] data = fis.readAllBytes();
        fis.close();
        response.getOutputStream().write(data);
    }
}
