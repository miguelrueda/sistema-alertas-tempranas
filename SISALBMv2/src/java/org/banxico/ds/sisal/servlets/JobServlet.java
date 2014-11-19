package org.banxico.ds.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.ejb.EJB;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.ejb.ActualizarCPELocal;
import org.banxico.ds.sisal.ejb.AnalizarBeanLocal;
import org.banxico.ds.sisal.ejb.UpdateBeanLocal;

/**
 * Servlet que se encarga de inicializar las tareas agendadas
 *
 * @author t41507
 * @version 07.08.2014
 */
public class JobServlet extends HttpServlet {

    /**
     * Inyección de los enterprise beans
     */
    @EJB
    private AnalizarBeanLocal analizarBean;
    @EJB
    private UpdateBeanLocal updateBean;
    @EJB
    private ActualizarCPELocal actualizarCpe;
    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(JobServlet.class.getName());

    /**
     * Método de inicialización del servlet, se inicia cuando en el descriptor
     * se tiene como iniciador el valor 1
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            //Iniciar los timers
            updateBean.setTimer();
            analizarBean.setTimer();
            actualizarCpe.setTimer();

        } catch (Exception e) {
            LOG.log(Level.INFO, "JobServlet#init() - Ocurrio un error al establecer los timers: {0}", e.getMessage());
        }
    }

    /**
     * Método que procesa las solicitudes del servlet
     *
     * @param request objeto de solicitud
     * @param response objeto de respuesta
     * @throws ServletException
     * @throws IOException
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(generarHTML());
    }

    /**
     * Método doGet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, "ocurrio un error al parsear la fecha de ejecuci\u00f3n: {0}", ex.getMessage());
        }
    }

    /**
     * Método doPost
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, "ocurrio un error al parsear la fecha de ejecuci\u00f3n: {0}", ex.getMessage());
        }
    }

    /**
     * Método que genera el código HTML de la vista de tareas programadas
     *
     * @return cadena con el contenido generado
     */
    private String generarHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<title>Tareas Programadas</title>")
                .append("<link href=\"resources/css/general.css\" type=\"text/css\" rel=\"stylesheet\" />")
                .append("<link href=\"resources/css/menu.css\" type=\"text/css\" rel=\"stylesheet\"/>")
                .append("</head>")
                .append("<body>")
                .append("<div id=\"page_container\">")
                .append("<div id=\"page_header\">")
                .append("<table id=\"header\">")
                .append("<tr>")
                .append("<td><img src=\"resources/images/app_header.png\" alt=\"BMLogo\" /></td>")
                .append("</tr>")
                .append("</table>")
                .append("</div>")
                .append("<div id=\"page_content\">")
                .append("<div id=\"workarea\">")
                .append("<div id=\"cssmenu\">")
                .append("<ul>")
                .append("<li class=\"has-sub\"><a href=\"#\"><span>Configuración</span></a>")
                .append("<ul style=\"z-index: 999\">")
                .append("<li class=\"has-sub\"><a href=\"#\"><span>Fuentes de Información</span></a>")
                .append("<ul>")
                .append("<li><a href='/sisalbm/admin/configuration/agregarFuente.jsp'><span>Agregar Fuente</span></a></li>")
                .append("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=1\"><span>Fuentes Registradas</span></a></li>")
                .append("</ul>")
                .append("</li>")
                .append("<li class=\"has-sub\"><a href=\"#\"><span>Grupos de Software</span></a>")
                .append("<ul>")
                .append("<li><a href=\"/sisalbm/admin/configuration/agregarGrupo.jsp\"><span>Agregar Nuevo Grupo</span></a></li>")
                .append("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=2\"><span>Grupos Registrados</span></a></li>")
                .append("</ul>")
                .append("</li>")
                .append("<li class=\"has-sub\"><a href=\"#\"><span>Software Disponible</span></a>")
                .append("<ul>")
                .append("<li><a href=\"/sisalbm/admin/vulnerabilities/addSW.jsp\"><span>Agregar Nuevo Software</span></a></li>")
                .append("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=3\"><span>Software Registrado</span></a></li>")
                .append("</ul>")
                .append("</li>")
                .append("<li class=\"last\"><a href=\"/sisalbm/JobScheduleServlet\"><span>Tareas Programadas</span></a></li>")
                .append("</ul>")
                .append("</li>")
                .append("<li><a href=\"#\"><span>Información de Vulnerabilidades</span></a>")
                .append("<ul>")
                .append("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=1\"><span>Más Recientes</span></a></li>")
                .append("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=2\"><span>Archivo</span></a></li>")
                .append("</ul>")
                .append("</li>")
                .append("<li><a href=\"/sisalbm/admin/scanner/scan.jsp\"><span>Búsqueda de Vulnerabilidades</span></a></li>")
                .append("<li><a href=\"/sisalbm/admin/help.jsp\"><span>Ayuda</span></a></li>")
                .append("</ul>")
                .append("</div>")
                .append("<br />")
                .append("<br />")
                .append("<div id=\"content_wrap\">")
                .append("<div id=\"content\">")
                .append("");
        SimpleDateFormat formater = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        sb.append("<div class='datagrid'>")
                .append("<table border=\"1\" cellpadding=\"5\" cellspacing=\"5\" id='tablestyle' style='text-align:center'>")
                .append("<thead>")
                .append("<tr>")
                .append("<th>")
                .append("Tarea")
                .append("</th>")
                .append("<th>")
                .append("Descripción")
                .append("</th>")
                .append("<th>")
                .append("Periodo De Ejecución")
                .append("</th>")
                .append("<th>")
                .append("Ultima Ejecución")
                .append("</th>")
                .append("<th>")
                .append("Siguiente Ejecución")
                .append("</th>")
                .append("</tr>")
                .append("</thead>")
                //Cuerpo de la tabla
                .append("<tbody>")
                .append("<tr>")
                .append("<td>")
                //Descripción de la tarea
                .append(updateBean.getDescripcion())
                .append("</td>")
                .append("<td>")
                .append("Se descargan de actualizaciones de vulnerabilidades a partir de las fuentes de información registradas.")
                .append("</td>")
                .append("<td>")
                .append("Ejecución Diaria")
                .append("</td>")
                .append("<td>");
        if (updateBean.getUltimaEjecucion() != null) {
            sb.append(formater.format(updateBean.getUltimaEjecucion()));
        } else {
            sb.append("No ejecutada");
        }
        sb.append("</td>");
        sb.append("<td>");
        //Siguiente ejecución
        if (updateBean.getNextFireTime() != null) {
            sb.append(formater.format(updateBean.getNextFireTime()));
        } else {
            sb.append("No disponible");
        }
        sb.append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td>")
                //Descripción de la tarea
                .append(analizarBean.getDescripcion())
                .append("</td>")
                .append("<td>")
                .append("Se ejecuta una búsqueda de vulnerabilidades sobre los grupos registrados en el sistema.")
                .append("</td>")
                .append("<td>")
                .append("Ejecución Diaria")
                .append("</td>")
                .append("<td>");
        if (analizarBean.getUltimaEjecucion() != null) {
            sb.append(formater.format(analizarBean.getUltimaEjecucion()));
        } else {
            sb.append("No ejecutada");
        }
        sb.append("</td>");
        sb.append("<td>");
        //Siguiente ejecución
        if (analizarBean.getNextFireTime() != null) {
            sb.append(formater.format(analizarBean.getNextFireTime()));
        } else {
            sb.append("No disponible");
        }
        sb.append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td>")
                //Descripción de la tarea
                .append(actualizarCpe.getDescripcion())
                .append("</td>")
                .append("<td>")
                .append("Se actualiza el catalogo de productos que se pueden registrar en el sistema.")
                .append("</td>")
                .append("<td>")
                .append("Ejecución Quincenal")
                .append("</td>")
                .append("<td>");
        if (actualizarCpe.getUltimaEjecucion() != null) {
            sb.append(formater.format(actualizarCpe.getUltimaEjecucion()));
        } else {
            sb.append("No ejecutada");
        }
        sb.append("</td>");
        sb.append("<td>");
        //Siguiente ejecución
        if (actualizarCpe.getNextFireTime() != null) {
            sb.append(formater.format(actualizarCpe.getNextFireTime()));
        } else {
            sb.append("No disponible");
        }
        sb.append("</td>")
                .append("</tr>")
                .append("</tbody>")
                .append("</table>")
                .append("</div>")
                .append("<br />")
                .append("<br />")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");
        return sb.toString();
    }

}
