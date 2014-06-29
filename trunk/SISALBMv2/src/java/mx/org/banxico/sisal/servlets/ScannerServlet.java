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
                if (val.equalsIgnoreCase("ua")) {
                    List<String> uasList = swdao.obtenerUAs();
                    out.println("<option value=''>Seleccionar UA</option>");
                    for (String ua : uasList) {
                        out.println("<option value='" + ua + "'>" + ua + "</option>");
                    }
                } else if (val.equalsIgnoreCase("vendor")) {
                    List<String> vendorList = swdao.obtenerFabricantes();
                    out.println("<option value=''>Seleccionar Fabricante</option>");
                    for (String vendor : vendorList) {
                        out.println("<option value='" + vendor + "'>" + vendor + "</option>");
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
