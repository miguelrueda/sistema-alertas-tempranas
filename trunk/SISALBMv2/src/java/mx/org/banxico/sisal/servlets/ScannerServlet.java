package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.org.banxico.sisal.dao.SoftwareDAO;
import mx.org.banxico.sisal.entities.Software;

public class ScannerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SoftwareDAO swdao = new SoftwareDAO();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String action = (String) request.getParameter("action");
            if (action.equalsIgnoreCase("retrieve")) {
                String val = (String) request.getParameter("val");
                List<Software> swList = swdao.obtenerTodos();
                if (val.equalsIgnoreCase("ua")) {
                    out.println("<option value=''>Seleccionar UA</option>");
                    for (Software sw : swList) {
                        out.println("<option value='" + sw.getUAResponsable() + "'>" + sw.getUAResponsable().toUpperCase() + "</option>");
                    }
                } else if (val.equalsIgnoreCase("vendor")) {
                    out.println("<option value=''>Seleccionar Fabricante</option>");
                    for (Software sw : swList) {
                        out.println("<option value='" + sw.getFabricante() + "'>" + sw.getFabricante() + "</option>");
                    }
                }
            }

        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
