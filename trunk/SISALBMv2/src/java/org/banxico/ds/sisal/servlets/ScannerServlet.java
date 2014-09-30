package org.banxico.ds.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;
import org.banxico.ds.sisal.util.MailBean;

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
     * Variables globales de Buffer y almacenamiento de Resultados
     */
    private StringBuilder exportBuffer;
    private Set<Result> resultadosEnvio = null;
    private MailBean mailService;
    
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
        PrintWriter out = response.getWriter();
        //Instanciar dao y bean de escaneo
        SoftwareDAO swdao = new SoftwareDAO();
        ScannerBean scannerService = new ScannerBean();
        mailService = new MailBean();
        try {
            //Solicitar el parametro action
            String action = (String) request.getParameter("action");
            //Atributos de paginador
            int page = 1; int recordsPerPage = 20;
            //Si el parametro es retrieve completar los campos del formulario
            if (action.equalsIgnoreCase("retrieve")) {
                response.setContentType("text/html;charset=UTF-8");
                //Obtener el parametro val 
                String val = (String) request.getParameter("val");
                //Buscar por UA/Grupo
                if (val.equalsIgnoreCase("ua")) {
                    List<String> uasList = swdao.obtenerUAs();
                    out.println("<option value='0'>Todos los Grupos/Todas las UA</option>");
                    for (String ua : uasList) {
                        out.println("<option value='" + ua + "'>" + ua + "</option>");
                    }
                    //Buscar por proveedor
                } else if (val.equalsIgnoreCase("vendor")) {
                    String retVendor = request.getParameter("vendor");
                    //Todos los vendedores
                    if (retVendor.equals("0")) {    //Obtener todos
                        List<String> vendorList = swdao.obtenerFabricantes();
                        out.println("<option value='0'>Seleccionar Fabricante</option>");
                        for (String vendor : vendorList) {
                            out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                        }
                        //Vendedor especifico
                    } else { //Obtener específico
                        List<String> vendorList = swdao.obtenerFabricantes(retVendor);
                        for (String vendor : vendorList) {
                            out.println("<option value='" + vendor + "'>" + vendor + "</option>");
                        }
                    }
                    //Buscar por producto
                } else if (val.equalsIgnoreCase("product")) {
                    String fabricante = request.getParameter("vendor");
                    if (!fabricante.equals("0")) {
                        List<String> productos = swdao.obtenerProductosPorFabricante(fabricante);
                        for (String product : productos) {
                            out.println("<option value='" + product + "'>" + product + "</option>");
                        }
                    }
                }
                //Si el parametro es scan, realizar el escaneo correspondiente
            } else if (action.equalsIgnoreCase("scan")) {
                response.setContentType("text/html;charset=UTF-8");
                //Solicitar el parametro tipo
                String tipo = (String) request.getParameter("tipo");
                //Inicializar las variables globales
                exportBuffer = new StringBuilder();
                //Tipo completo realizar analisis en todos los registros existentes 
                // o por fecha especifica
                if (tipo.equalsIgnoreCase("completo")) {
                    String fecha = (String) request.getParameter("fechaF");
                    Set<Result> resultados = null;
                    exportBuffer.append("<p>Resultados del Escaneo Completo<br/>");
                    if (fecha.equalsIgnoreCase("full")) {
                        resultados = scannerService.doCompleteScan();
                    } else if (fecha.equalsIgnoreCase("partial")) {
                        String sdate = (String) request.getParameter("sdateF");
                        String edate = (String) request.getParameter("edateF");
                        resultados = scannerService.doCompleteScan(sdate, edate);
                    }
                    //Agregar resultados al buffer de exportacion
                    for (Result result : resultados) {
                        //Traducir la gravedad
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
                        exportBuffer.append("<br />Descripción: ").append(result.getVulnerabilidad().getDescription());
                        exportBuffer.append("<br/>Los grupos afectados son: ");
                        for (String grupo : result.getGruposList()) {
                            exportBuffer.append("<br /> + ").append(grupo);
                        }
                        exportBuffer.append("</p><br />");
                    }
                    request.setAttribute("resultados", resultados);
                    prepararEnvio(resultados);
                    request.setAttribute("noOfResults", resultados.size());
                    request.setAttribute("exportBuffer", exportBuffer);
                } else if (tipo.equalsIgnoreCase("custom")) {
                    //En este tipo de analisis se parametrizan todas las opciones
                    String vulnt = (String) request.getParameter("vulnt");
                    //Realizar por vulnerabilidades recientes o archivo
                    if (vulnt.equalsIgnoreCase("recent")) {
                        scannerService.setPartialType(1);
                    } else if (vulnt.equalsIgnoreCase("todas")) {
                        scannerService.setPartialType(2);
                    }
                    //Realizar analisis en todos los grupos o en grupo especifico
                    String UA = (String) request.getParameter("UA");
                    if (UA.equalsIgnoreCase("0")) {
                        scannerService.setUA("0");
                        exportBuffer.append("<p>Resultado de Todos los Grupos<br />");
                    } else {
                        scannerService.setUA(UA);
                        request.setAttribute("grupo", UA);
                        exportBuffer.append("<p>Resultado del Grupo: <u>")
                                .append(UA)
                                .append("</u>");
                    }
                    //Realizar analisis en vulnerabilidades que han sido publicadas o modificadas
                    String onlyPublished = request.getParameter("onlypub");
                    if (onlyPublished != null && onlyPublished.equalsIgnoreCase("onlypub")) {
                        scannerService.setModificadas(true);
                    } else if (onlyPublished == null) {
                        scannerService.setModificadas(false);
                    }
                    //Realizar analisis por fabricante unico o por todos
                    String fab = (String) request.getParameter("fab");
                    if (fab.equalsIgnoreCase("single")) {
                        scannerService.setVendorType(1);
                        String vendor = (String) request.getParameter("vendor");
                        scannerService.setVendor(vendor);
                        exportBuffer.append("<br/> Resultados del Fabricante: <u>").append(vendor).append("</u><br />");
                    }
                    //Realizar analisis por tipo de criticidad baja media o alta
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
                    //Realizar analisis por fecha especifica o por periodo completo
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
                    //LLamar al servicio para realizar el analisis
                    Set<Result> resultados = scannerService.doPartialScan();
                    //Añadir resultados al buffer de exportacion
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
                        exportBuffer.append("La vulnerabilidad <u>")
                                .append(result.getVulnerabilidad().getName())
                                .append("</u> " + "publicada el ")
                                .append(fmt.format(result.getVulnerabilidad().getPublished()))
                                .append(" de gravedad: ")
                                .append(es_sev)
                                .append(" ");
                        exportBuffer.append("puede afectar al Software: ");
                        for (Software sw : result.getSwList()) {
                            exportBuffer.append("<br /> + ").append(sw.getNombre());
                        }
                        exportBuffer.append("<br/>Descripción: ").append(result.getVulnerabilidad().getDescription());
                        exportBuffer.append("<br/>Los grupos afectados son: ");
                        for (String grupo : result.getGruposList()) {
                            exportBuffer.append("<br /> + ").append(grupo);
                        }
                        exportBuffer.append("</p><br />");
                    }
                    request.setAttribute("resultados", resultados);
                    prepararEnvio(resultados);
                    request.setAttribute("noOfResults", resultados.size());
                    request.setAttribute("exportBuffer", exportBuffer);
                    //Error
                } else {
                    //Cuando la peticion no es conocida se lanza un error
                    response.getWriter().write("Error desconocido");
                }
                //Redirección a resultados
                String nextJSP = "/admin/scanner/results.jsp";
                RequestDispatcher view = this.getServletContext().getRequestDispatcher(nextJSP);
                view.forward(request, response);
            } else if (action.equalsIgnoreCase("export")) {
            } else if (action.equalsIgnoreCase("sendResults")) {
                response.setContentType("text/html;charset=UTF-8");
                LOG.log(Level.INFO, "Se recibio una petición de tipo sendResults");
                boolean enviado = enviarCorreo();
                if (enviado) {
                    out.println("ENVIADO");
                } else if (!enviado) {
                    out.println("NOENVIADO");
                } else {
                    out.println("ERROR");
                }
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

    private void prepararEnvio(Set<Result> resultados) {
        LOG.log(Level.INFO, "ScannerServlet() - Preparando contenido del correo");
        resultadosEnvio = new LinkedHashSet<Result>();
        resultadosEnvio.addAll(resultados);
    }

    private boolean enviarCorreo() {
        boolean flag = false;
        flag = mailService.enviarCorreodeResultados("Resultados del Análisis Personalizado", resultadosEnvio);
        if (flag) {
            LOG.log(Level.INFO, "ScannerServlet#enviarCorreo() - Los resultados fueron enviados al administrador");
        } else if (!flag) {
            LOG.log(Level.INFO, "ScannerServlet#enviarCorreo() - Ocurrio un error al enviar los resultados");
        } else {
            LOG.log(Level.INFO, "ScannerServlet#enviarCorreo() - Ocurrio un problema al realizar la petición");
        }
        return flag;
    }

    
}
