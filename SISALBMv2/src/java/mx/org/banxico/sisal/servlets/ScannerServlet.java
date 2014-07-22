package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.org.banxico.sisal.dao.SoftwareDAO;
import mx.org.banxico.sisal.entities.Software;
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
    private StringBuilder exportBuffer;
    
    /**
     * Método que se encarga de procesar las solicituds
     *
     * @param request referencia de solicitud
     * @param response referencia de respuesta
     * @throws ServletException
     * @throws IOException
     *
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SoftwareDAO swdao = new SoftwareDAO();
        ScannerBean scannerService = new ScannerBean();
        PrintWriter out = response.getWriter();
        try {
            String action = (String) request.getParameter("action");
            int page = 1; int recordsPerPage = 20;
            if (action.equalsIgnoreCase("retrieve")) {
                response.setContentType("text/html;charset=UTF-8");
                String val = (String) request.getParameter("val");
                if (val.equalsIgnoreCase("ua")) {
                    List<String> uasList = swdao.obtenerUAs();
                    out.println("<option value='0'>Todos los Grupos/Todas las UA</option>");
                    for (String ua : uasList) {
                        out.println("<option value='" + ua + "'>" + ua + "</option>");
                    }
                } else if (val.equalsIgnoreCase("vendor")) {
                    String retVendor = request.getParameter("vendor");
                    if (retVendor.equals("0")) {    //Obtener todos
                        List<String> vendorList = swdao.obtenerFabricantes();
                        out.println("<option value='0'>Seleccionar Fabricante</option>");
                        for (String vendor : vendorList) {
                            out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                        }
                    } else { //Obtener específico
                        List<String> vendorList = swdao.obtenerFabricantes(retVendor);
                        for (String vendor : vendorList) {
                            out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                        }
                    }
                }
            } else if (action.equalsIgnoreCase("scan")) {
                response.setContentType("text/html;charset=UTF-8");
                String tipo = (String) request.getParameter("tipo");
                exportBuffer = new StringBuilder();
                if (tipo.equalsIgnoreCase("completo")) {
                    String fecha = (String) request.getParameter("fechaF");
                    Set<Result> resultados = null;
                    exportBuffer.append("<p>Resultados del Escaneo Completo</p>");
                    if (fecha.equalsIgnoreCase("full")) {
                        //Set<Result> 
                        resultados = scannerService.doCompleteScan();
                    } else if (fecha.equalsIgnoreCase("partial")) {
                        String sdate = (String) request.getParameter("sdateF");
                        String edate = (String) request.getParameter("edateF");
                        //Set<Result> 
                        resultados = scannerService.doCompleteScan(sdate, edate);
                        //request.setAttribute("resultados", resultados);
                        //request.setAttribute("noOfResults", resultados.size());
                    }
                    for (Result result : resultados) {
                        String sev = result.getVulnerabilidad().getSeverity();
                        String es_sev = "";
                        if (sev.equalsIgnoreCase("high")) {
                            es_sev = "Alta";
                        } else if (sev.equalsIgnoreCase("medium")) {
                            es_sev = "Medium";
                        } else if (sev.equalsIgnoreCase("low")) {
                            es_sev = "Baja";
                        } else {
                            es_sev = "ND";
                        }
                        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        exportBuffer.append("La vulnerabilidad <u>").append(result.getVulnerabilidad().getName())
                                .append("</u> " + "publicada el: ").append(fmt.format(result.getVulnerabilidad().getPublished()))
                                .append(" de gravedad ").append(result.getVulnerabilidad().getSeverity())
                                .append(" ");
                        exportBuffer.append("puede afectar al Software: ");
                        for (Software sw : result.getSwList()) {
                            exportBuffer.append("<br /> + ").append(sw.getNombre());
                        }
                        exportBuffer.append("<br />Descripción: ").append(result.getVulnerabilidad().getDescription()).append("<br/>");
                        exportBuffer.append("<br/>Los grupos afectados son: ");
                        for (String grupo : result.getGruposList()) {
                            exportBuffer.append("<br /> + ").append(grupo);
                        }
                        exportBuffer.append("</p><br />");
                    }
                    request.setAttribute("resultados", resultados);
                    request.setAttribute("noOfResults", resultados.size());
                    request.setAttribute("exportBuffer", exportBuffer);
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
                        //scanGroup = "Todos los Grupos";
                        exportBuffer.append("<p>Resultado de Todos los Grupos</p>");
                    } else {
                        //Traer UA con SW DAo
                        scannerService.setUA(UA);
                        request.setAttribute("grupo", UA);
                        //scanGroup = UA;
                        exportBuffer.append("<p>Resultado del Grupo: ").append(UA).append("</p>");
                    }
                    String onlyPublished = request.getParameter("onlypub");
                    if (onlyPublished != null && onlyPublished.equalsIgnoreCase("onlypub")) {
                        scannerService.setModificadas(true);
                    } else if (onlyPublished == null) {
                        scannerService.setModificadas(false);
                    }
                    String fab = (String) request.getParameter("fab");
                    if (fab.equalsIgnoreCase("single")) {
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
                    for (Result result : resultados) {
                        String sev = result.getVulnerabilidad().getSeverity();
                        String es_sev = "";
                        if (sev.equalsIgnoreCase("high")) {
                            es_sev = "Alta";
                        } else if (sev.equalsIgnoreCase("medium")) {
                            es_sev = "Media";
                        } else if (sev.equalsIgnoreCase("low")) {
                            es_sev = "Baja";
                        } else {
                            es_sev = "ND";
                        }
                        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        exportBuffer.append("La vulnerabilidad <u>").append(result.getVulnerabilidad().getName()).append("</u> " + "publicada el ").append(fmt.format(result.getVulnerabilidad().getPublished())).append(" de gravedad: ").append(es_sev).append(" ");
                        exportBuffer.append("puede afectar al Software: ");
                        for (Software sw : result.getSwList()) {
                            exportBuffer.append("<br /> + ").append(sw.getNombre());
                        }
                        exportBuffer.append("<br/>Descripción: ").append(result.getVulnerabilidad().getDescription()).append("<br />");
                        exportBuffer.append("<br/>Los grupos afectados son: ");
                        for (String grupo : result.getGruposList()) {
                            exportBuffer.append("<br /> + ").append(grupo);
                        }
                        exportBuffer.append("</p><br />");
                    }
                    request.setAttribute("resultados", resultados);
                    request.setAttribute("noOfResults", resultados.size());
                    request.setAttribute("exportBuffer", exportBuffer);
                } else {
                    response.getWriter().write("Error desconocido");
                }
                String nextJSP = "/admin/scanner/results.jsp";
                RequestDispatcher view = this.getServletContext().getRequestDispatcher(nextJSP);
                view.forward(request, response);
            } else if (action.equalsIgnoreCase("export")) {
            }
        } finally {
            out.close();
        }
    }

    /**
     * Método doGet
     *
     * @param request referencia de solicitud
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
