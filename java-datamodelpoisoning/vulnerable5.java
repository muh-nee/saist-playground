package main;

import org.tensorflow.SavedModelBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SavedModelBundle.load(req.getParameter("model_path"), "serve");
        resp.getWriter().write("ok");
    }
}
