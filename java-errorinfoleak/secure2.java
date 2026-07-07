import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class secure2 {
    private static final Logger logger = LoggerFactory.getLogger(secure2.class);

    public void handleError(Exception e, HttpServletResponse response) throws IOException {
        logger.error("Request processing failed", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "internal server error");
    }
}
