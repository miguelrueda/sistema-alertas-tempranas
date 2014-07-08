package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.org.banxico.sisal.dao.SoftwareDAO;
import mx.org.banxico.sisal.scanner.Result;
import mx.org.banxico.sisal.scanner.ScannerBean;

/**
 * Controlador para las peticiones del escaner
 *
 * @author t41507
 * @version 02072014
 */
public class ScannerServlet extends HttpServlet implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ScannerServlet.class.getName());

    /**
     * Método que se encarga de procesar las solicituds
     *
     * @param request referencia de solicitud
     * @param response referencia de respuesta
     * @throws ServletException
     * @throws IOException
     */
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
                    //List<String> uasList = swdao.obtenerUAsTemp();
                    Set<String> uasList = swdao.obtenerUAsTemp();
                    out.println("<option value='0'>Todos los Grupos/Todas las UA</option>");
                    for (String ua : uasList) {
                        out.println("<option value='" + ua + "'>" + ua + "</option>");
                    }
                } else if (val.equalsIgnoreCase("vendor")) {
                    //TODO: descomentar la linea siguiente
                    //List<String> vendorList = swdao.obtenerFabricantes();
                    Set<String> vendorList = swdao.obtenerFabricantesTemp();
                    out.println("<option value='0'>Seleccionar Fabricante</option>");
                    for (String vendor : vendorList) {
                        out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                    }
                }
            } else if (action.equalsIgnoreCase("scan")) {
                String tipo = (String) request.getParameter("tipo");
                if (tipo.equalsIgnoreCase("completo")) {
                    String fecha = (String) request.getParameter("fechaF");
                    if (fecha.equalsIgnoreCase("full")) {
                        Set<Result> resultados = scannerService.doCompleteScan();
                        request.setAttribute("resultados", resultados);
                        request.setAttribute("noOfResults", resultados.size());
                    } else if (fecha.equalsIgnoreCase("partial")) {
                        String sdate = (String) request.getParameter("sdateF");
                        String edate = (String) request.getParameter("edateF");
                        Set<Result> resultados = scannerService.doCompleteScan(sdate, edate);
                        request.setAttribute("resultados", resultados);
                        request.setAttribute("noOfResults", resultados.size());
                    }
                } else if (tipo.equalsIgnoreCase("custom")) {
                    String vulnt = (String) request.getParameter("vulnt");
                    if (vulnt.equalsIgnoreCase("recent")) {
                        scannerService.setPartialType(1);
                    } else if (vulnt.equalsIgnoreCase("todas")) {
                        scannerService.setPartialType(2);
                    }
                    String UA = (String) request.getParameter("UA");
                    if (UA.equalsIgnoreCase("0")) {
                        scannerService.setUA("0");
                    } else {
                        //Traer UA con SW DAo
                        scannerService.setUA(UA);
                    }
                    String fab = (String) request.getParameter("fab");
                    if (fab.equalsIgnoreCase("single")) {
                        //Trear fabricante con sW Dao
                        scannerService.setVendorType(1);
                        String vendor = (String) request.getParameter("vendor");
                        scannerService.setVendor(vendor);
                    }
                    String critic = (String) request.getParameter("critic");
                    switch (Integer.parseInt(critic)) {
                        case 0:
                            scannerService.setSeverity("nd");
                            break;
                        case 1:
                            scannerService.setSeverity("low");
                            break;
                        case 2:
                            scannerService.setSeverity("medium");
                            break;
                        case 3:
                            scannerService.setSeverity("high");
                            break;
                    }
                    String fecha = (String) request.getParameter("fechaC");
                    String sdate = null;
                    String edate = null;
                    if (fecha.equalsIgnoreCase("full")) {
                        scannerService.setDateType("full");
                    } else if (fecha.equalsIgnoreCase("partial")) {
                        sdate = request.getParameter("sdateC");
                        edate = request.getParameter("edateC");
                        scannerService.setDateType("partial");
                        scannerService.setSdate(sdate);
                        scannerService.setEdate(edate);
                    }
                    Set<Result> resultados = scannerService.doPartialScan();
                    request.setAttribute("resultados", resultados);
                    request.setAttribute("noOfResults", resultados.size());
                } else {
                    response.getWriter().write("Error desconocido");
                }
                String nextJSP = "/admin/scanner/results.jsp";
                RequestDispatcher view = this.getServletContext().getRequestDispatcher(nextJSP);
                view.forward(request, response);
            }

        } finally {
            out.close();
        }
    }

    /**
     * Método doGet
     *
     * @param request referncia de solicitud
     * @param response referencia de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Método doPost
     *
     * @param request referncia de solicitud
     * @param response referencia de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
