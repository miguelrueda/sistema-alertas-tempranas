package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.org.banxico.sisal.dao.SoftwareDAO;
import mx.org.banxico.sisal.scanner.Result;
import mx.org.banxico.sisal.scanner.ScannerBean;

public class ScannerServlet extends HttpServlet implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ScannerServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SoftwareDAO swdao = new SoftwareDAO();
        ScannerBean scannerService = new ScannerBean();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String action = (String) request.getParameter("action");
            if (action.equalsIgnoreCase("retrieve")) {
                String val = (String) request.getParameter("val");
                if (val.equalsIgnoreCase("ua")) {
                    //TODO: descomentar la linea siguiente
                    //List<String> uasList = swdao.obtenerUAs();
                    List<String> uasList = swdao.obtenerUAsTemp();
                    out.println("<option value='0'>Todas las UA</option>");
                    for (String ua : uasList) {
                        out.println("<option value='" + ua + "'>" + ua + "</option>");
                    }
                } else if (val.equalsIgnoreCase("vendor")) {
                    //TODO: descomentar la linea siguiente
                    //List<String> vendorList = swdao.obtenerFabricantes();
                    List<String> vendorList = swdao.obtenerFabricantesTemp();
                    out.println("<option value=''>Seleccionar Fabricante</option>");
                    for (String vendor : vendorList) {
                        out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                    }
                } //h/sisalbm/scanner?tipo=completo&fechaC=fulldate&sdateC=&edateC=&UA=0&vendor=&sdate=&edate=
            } else if (action.equalsIgnoreCase("scan")) {
                String tipo = (String) request.getParameter("tipo");
                if (tipo.equalsIgnoreCase("completo")) {
                    String fecha = (String) request.getParameter("fecha");
                    if (fecha.equalsIgnoreCase("fulldate")) {
                        String res = scannerService.doCompleteScan();
                        response.getWriter().write(res);
                        /*
                        Set<Result> resultados = scannerService.doCompleteScan();
                        int noOfResults = resultados.size();
                        request.setAttribute("resultados", resultados);
                        request.setAttribute("noOfResults", noOfResults);
                        LOG.log(Level.INFO, "Redireccionando al recurso: /scanner/results.jsp");
                        String nextJSP = "/admin/scanner/results.jsp";
                        RequestDispatcher view = getServletContext().getRequestDispatcher(nextJSP);
                        view.forward(request, response);
                                */
                    } else if (fecha.equalsIgnoreCase("partialdate")) {
                        response.getWriter().write("Not implemented yet.");
                    }
                } else if (tipo.equalsIgnoreCase("custom")) {
                    response.getWriter().write("Not implemented yet.");
                } else {
                    response.getWriter().write("Error desconocido");
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
