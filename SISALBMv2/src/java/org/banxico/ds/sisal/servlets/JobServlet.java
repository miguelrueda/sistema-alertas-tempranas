package org.banxico.ds.sisal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.ejb.EJB;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.NoSuchEJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //Codigo HTML
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Tareas Programadas</title>");
        out.println("<link href=\"resources/css/general.css\" type=\"text/css\" rel=\"stylesheet\" />");
        out.println("<link href=\"resources/css/menu.css\" type=\"text/css\" rel=\"stylesheet\"/>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"page_container\">");
        out.println("<div id=\"page_header\">");
        out.println("<table id=\"header\">");
        out.println("<tr>");
        out.println("<td><img src=\"resources/images/app_header.png\" alt=\"BMLogo\" /></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</div>");
        out.println("<div id=\"page_content\">");
        //out.println("<div id=\"title\">&nbsp;App Index</div>");
        out.println("<div id=\"workarea\">");
        out.println("<div id=\"cssmenu\">");
        out.println("<ul>");
        out.println("<li class=\"has-sub\"><a href=\"#\"><span>Configuración</span></a>");
        out.println("<ul style=\"z-index: 999\">");
        out.println("<li class=\"has-sub\"><a href=\"#\"><span>Fuentes</span></a>");
        out.println("<ul>");
        out.println("<li><a href='/sisalbm/admin/configuration/agregarFuente.jsp'><span>Agregar Fuente</span></a></li>");
        out.println("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=1\"><span>Fuentes Registradas</span></a></li>");
        out.println("</ul>");
        out.println("</li>");
        out.println("<li class=\"has-sub\"><a href=\"#\"><span>Grupos</span></a>");
        out.println("<ul>");
        out.println("<li><a href=\"/sisalbm/admin/configuration/agregarGrupo.jsp\"><span>Agregar Grupo</span></a></li>");
        out.println("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=2\"><span>Grupos Registrados</span></a></li>");
        out.println("</ul>");
        out.println("</li>");
        out.println("<li class=\"has-sub\"><a href=\"#\"><span>Software</span></a>");
        out.println("<ul>");
        out.println("<li><a href=\"/sisalbm/admin/vulnerabilities/addSW.jsp\"><span>Agregar Software</span></a></li>");
        out.println("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=3\"><span>Software Registrado</span></a></li>");
        out.println("</ul>");
        out.println("</li>");
        out.println("<li class=\"last\"><a href=\"/sisalbm/JobScheduleServlet\"><span>Tareas Programadas</span></a></li>");
        out.println("</ul>");
        out.println("</li>");
        out.println("<li><a href=\"#\"><span>Vulnerabilidades</span></a>");
        out.println("<ul>");
        out.println("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=1\"><span>Más Recientes</span></a></li>");
        out.println("<li><a href=\"/sisalbm/admin/vulnerability.controller?action=view&tipo=2\"><span>Archivo</span></a></li>");
        out.println("</ul>");
        out.println("</li>");
        out.println("<li><a href=\"/sisalbm/admin/scanner/scan.jsp\"><span>Escaneo</span></a></li>");
        out.println("<li><a href=\"/sisalbm/admin/help.jsp\"><span>Ayuda</span></a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<br />");
        out.println("<br />");
        out.println("<div id=\"content_wrap\">");
        out.println("<div id=\"content\">");
        out.println("");
        SimpleDateFormat formater = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        out.println("<div class='datagrid'>");
        out.println("<table border=\"1\" cellpadding=\"5\" cellspacing=\"5\" id='tablestyle' style='text-align:center'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>");
        out.println("Tarea");
        out.println("</th>");
        out.println("<th>");
        out.println("Ultima Ejecución");
        out.println("</th>");
        out.println("<th>");
        out.println("Siguiente Ejecución");
        out.println("</th>");
        out.println("</tr>");
        out.println("</thead>");
        //Cuerpo de la tabla
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td>");
        //Descripción de la tarea
        out.println(updateBean.getDescripcion());
        out.println("</td>");
        out.println("<td>");
        if (updateBean.getUltimaEjecucion() != null) {
            out.println(formater.format(updateBean.getUltimaEjecucion()));
        } else {
            out.println("No ejecutada");
        }
        out.println("</td>");
        out.println("<td>");
        //Siguiente ejecución
        out.println(formater.format(updateBean.getNextFireTime()));
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        //Descripción de la tarea
        out.println(analizarBean.getDescripcion());
        out.println("</td>");
        out.println("<td>");
        if (analizarBean.getUltimaEjecucion() != null) {
            out.println(formater.format(analizarBean.getUltimaEjecucion()));
        } else {
            out.println("No ejecutada");
        }
        out.println("</td>");
        out.println("<td>");
        //Siguiente ejecución
        out.println(formater.format(analizarBean.getNextFireTime()));
        out.println("</td>");
        out.println("</tr>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("<br />");
        out.println("<br />");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
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

}
