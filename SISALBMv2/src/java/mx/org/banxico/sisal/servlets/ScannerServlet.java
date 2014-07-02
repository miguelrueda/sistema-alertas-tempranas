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
                    String fecha = (String) request.getParameter("fechaF");
                    if (fecha.equalsIgnoreCase("full")) {
                        //String res = scannerService.doCompleteScan();
                        //response.getWriter().write(res);
                        Set<Result> resultados = scannerService.doCompleteScan();
                        for (Result result : resultados) {
                            result.getVulnerabilidad().getName();
                        }
                        int noOfResults = resultados.size();
                        request.setAttribute("resultados", resultados);
                        request.setAttribute("noOfResults", noOfResults);
                        String nextJSP = "/admin/scanner/results.jsp";
                        RequestDispatcher view = this.getServletContext().getRequestDispatcher(nextJSP);
                        view.forward(request, response);
                        //out.println("<h1>" + res + "</h1>");
                    } else if (fecha.equalsIgnoreCase("partial")) {
                        String sdate = (String) request.getParameter("sdateF");
                        String edate = (String) request.getParameter("edateF");
                        //int noOfResults = scannerService.doCompleteScan(sdate, edate);
                        Set<Result> resultados = scannerService.doCompleteScan(sdate, edate);
                        int noOfResults = resultados.size();
                        request.setAttribute("resultados", resultados);
                        request.setAttribute("noOfResults", noOfResults);
                        //out.println("<div id=\"page_title\">Not implemented yet</div>\n");
                        //out.println("<div id=\"content\">\n");
                        //response.getWriter().write("Filtrar con respecto a las fechas: " + sdate + " y " + edate);
                        //out.println(addPageBottom());
                        String nextJSP = "/admin/scanner/results.jsp";
                        RequestDispatcher view = this.getServletContext().getRequestDispatcher(nextJSP);
                        view.forward(request, response);
                    }
                } else if (tipo.equalsIgnoreCase("custom")) {
                    String vulnt = (String) request.getParameter("vulnt");
                    if (vulnt.equalsIgnoreCase("recent")) {
                        //Set solo recientes
                    } else if (vulnt.equalsIgnoreCase("todas")) {
                        //Set todo el archivo
                    }
                    String UA = (String) request.getParameter("UA");
                    if (UA.equalsIgnoreCase("0")) {

                    } else {
                        //Traer UA con SW DAo
                    }
                    String fab = (String) request.getParameter("fab");
                    if (fab.equalsIgnoreCase("single")) {
                        //Trear fabricante con sW Dao
                        String vendor = (String) request.getParameter("vendor");
                    } else {
                        //Aplicar a todos los fabricantes
                    }
                    String critic = (String) request.getParameter("critic");
                    //Validar que critic no sea 0
                    String fecha = (String) request.getParameter("fechaC");
                    String sdate = null;
                    String edate = null;
                    if (fecha.equalsIgnoreCase("full")) {

                    } else if (fecha.equalsIgnoreCase("partial")) {
                        sdate = request.getParameter("sdateC");
                        edate = request.getParameter("edateC");
                    }
                    request.setAttribute("noOfResults", 0);

                    out.println("Parametros: " + vulnt + " * " + UA + " * " + fab + " * " + critic + " * " + fecha + " * " + sdate + " * " + edate);
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

    private String addPageHead() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head>\n").append("<title>Resultados</title>\n").append("<meta charset=\"UTF-8\" />\n");
        sb.append("<meta name=\"viewport\" content=\"width=device-width\" />\n");
        sb.append("<link href=\"/sisalbm/resources/css/general.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
        sb.append("<link href=\"/sisalbm/resources/css/jquery-ui-1.10.4.custom.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
        sb.append("<script src=\"//code.jquery.com/jquery-1.10.2.js\"></script>\n");
        sb.append("<script src=\"//code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>\n");
        return sb.toString();
    }

    private String addPageContent(Set<Result> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"page_title\">Encontré: ").append(resultados.size()).append(" posibles amenazas.</div>\n");
        sb.append("<div id=\"content\">\n");
        for (Result result : resultados) {
            sb.append("<p>").append(result).append("</p>").append("\n");
        }
        return sb.toString();
    }

    private String addPageTop() {
        StringBuilder sb = new StringBuilder();
        sb.append("<body>\n");
        sb.append("<div id=\"page_container\">\n");
        sb.append("<div id=\"page_header\">\n");
        sb.append("<table id=\"header\">\n");
        sb.append("<tr>\n");
        sb.append("<td><img src=\"/sisalbm/resources/images/app_header.png\" alt=\"BMLogo\" /></td>\n");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append("</div>\n");//page_header
        sb.append("<div id=\"page_content\">\n");
        sb.append("<div id=\"title\">" + "Resultados" + "</div>\n");
        sb.append("<div id=\"workarea\">\n");
        sb.append("<div id=\"menu\">AGREGAR MENÚ</div>\n");   //menu
        sb.append("<div id=\"content_wrap\">\n");
        return sb.toString();
    }

    private String addPageBottom() {
        StringBuilder sb = new StringBuilder();
        sb.append("</div>\n");//content
        sb.append("</div>\n");//contentwrap
        sb.append("</div>\n");//workarea
        sb.append("</div>\n");//pagecontent
        sb.append("</div>\n");//pagecontainer
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }
}
