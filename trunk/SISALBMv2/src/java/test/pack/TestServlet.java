package test.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet implements java.io.Serializable {
    
    private static final long serialVersionUID = -1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = null;
        String ageS = null;
        name = "Hello " + request.getParameter("user");
        ageS = request.getParameter("age");
        if (request.getParameter("user").toString().equals("")) {
            name = "Hello User";
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(name + " - " + ageS);
   }

}