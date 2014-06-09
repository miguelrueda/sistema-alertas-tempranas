package jsf.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para procesar el escaneo y redirecciónar a los resultados
 *
 * @author t41507
 * @version 09.06.2014
 */
public class ScannerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("target") != null) {
            System.out.println("TARGET: " + request.getParameter("target"));
            RequestDispatcher dispatcher = request.getRequestDispatcher(request.getParameter("target"));
            dispatcher.forward(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ScannerServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet ScannerServlet at " + request.getContextPath() + "</h1>");
                out.println("<h2>Headers</h2>");
                Enumeration<String> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    String s = headers.nextElement();
                    out.println("<p>" + s + " : " + request.getHeader(s) + "</p>");
                }
                out.println("<h2>Test</h2>");
                out.println(request.getAttribute("nombre") + ":" + request.getAttribute("temp"));
                out.println(String.valueOf(request.getParameter("SCAN")));
                out.println("<h2>Attributes</h2>");
                Enumeration<String> attributes = request.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String s = attributes.nextElement();
                    out.println("<p>" + s + " : " + request.getAttribute(s) + "</p>");
                }
                out.println("<h2>Parameters</h2>");
                Enumeration<String> parameters = request.getParameterNames();
                while (parameters.hasMoreElements()) {
                    String s = parameters.nextElement();
                    out.println("<p>" + s + " : " + request.getParameter(s) + "</p>");
                }

                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
            } finally {
                out.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
