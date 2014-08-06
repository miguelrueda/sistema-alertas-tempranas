package org.banxico.ds.sisal.scheduled;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Date;
import java.util.List;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import java.text.SimpleDateFormat;

/**
 * Servlet que inicializa y obtiene la información de las tareas a ejecutar
 *
 * @author t41507
 * @version 04082014
 */
public class JobScheduleServlet extends HttpServlet {

    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(JobScheduleServlet.class.getName());
    /**
     * Instancia del scheduler
     */
    private JobScheduler jScheduler;

    /**
     * Iniciar el scheduler al iniciar el servlet
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        LOG.log(Level.INFO, "Iniciando el servlet scheduler: {0}", new Date());
        jScheduler = new JobScheduler();
        //jScheduler.start();
    }

    /**
     * Método que maneja las solicitudes del servlet
     *
     * @param request referencia de la solicitud
     * @param response referencia de la respuesta
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Contenido de la respuesta
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobScheduleServlet</title>");
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

            out.println("<div id=\"title\">&nbsp;App Index</div>");
            out.println("<div id=\"workarea\">");
            /*
             Menú
             */
            out.println("<div id=\"cssmenu\">");
            out.println("<ul>");
            //out.println("<li><a href=\"AppIndex.html\"><span>AppIndex</span></a></li>");
            out.println("<li class=\"has-sub\"><a href=\"#\"><span>Configuración</span></a>");
            out.println("<ul style=\"z-index: 999\">");
            out.println("<li class=\"has-sub\"><a href=\"#\"><span>Fuentes</span></a>");
            out.println("<ul>");
            out.println("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=1\"><span>Administrar</span></a></li>");
            out.println("</ul>");
            out.println("</li>");
            out.println("<li class=\"has-sub\"><a href=\"#\"><span>Grupos</span></a>");
            out.println("<ul>");
            out.println("<li><a href=\"/sisalbm/admin/configuration.controller?action=view&tipo=2\"><span>Administrar</span></a></li>");
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
            //out.println("<h1>Servlet JobScheduleServlet at " + request.getContextPath() + "</h1>");
            SimpleDateFormat formater = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
            Scheduler sched = jScheduler.getSched();
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
            out.println("<tbody>");
            //Se iteran todos los grupo del scheduler para obtener su informacion
            for (String grupo : sched.getJobGroupNames()) {
                //Se compara cada llave para obtener incidencias
                for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(grupo))) {
                    //Se obtiene nombre, grupo e información del trigger
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    List<Trigger> triggers = (List<Trigger>) sched.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    Date previousFireTime = triggers.get(0).getPreviousFireTime();
                    //Contenido de la respuesta
                    out.println("<tr>");
                    out.println("<td>" + jobName + "</td>");
                    if (previousFireTime != null) {
                        out.println("<td>" + formater.format(previousFireTime) + "</td>");
                    } else {
                        out.println("<td>No definida</td>");
                    }
                    if (nextFireTime != null) {
                        out.println("<td>" + formater.format(nextFireTime) + "</td>");
                    } else {
                        out.println("<td>No definida</td>");
                    }
                    //out.println("[jobName]: " + jobName + " [groupName]: " + jobGroup + " [NFT]: " + nextFireTime);
                    out.println("</tr>");
                }
            }
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
        } catch (SchedulerException ex) {
            Logger.getLogger(JobScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    /**
     * Método que procesa la solicitud Get
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Método que procesa la solicitud Post
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
