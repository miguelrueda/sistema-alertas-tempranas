package org.banxico.ds.sisal.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;
import java.text.SimpleDateFormat;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Grupo;
import org.banxico.ds.sisal.entities.Vulnerabilidad;

public class DashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DashboardServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        try {
            StringBuilder sb = new StringBuilder();
            ScannerBean scanner = new ScannerBean();
            String action = request.getParameter("action");
            if (action != null && !"".equals(action)) {
                if (action.equalsIgnoreCase("cuentaFabs")) {

                } else if (action.equalsIgnoreCase("recientes")) {
                    List<Vulnerabilidad> resultados = vdao.obtenerVulnerabilidadesRecientes();
                    LOG.log(Level.INFO, "DashboardServlet#processRequest() - Encontre {0} resultados.", resultados.size());
                    //<table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                    sb.append("<table class='center' border='1' cellpadding='5' cellspacing='5'>");
                    sb.append("<thead>");
                    sb.append("<td>")
                            .append("Id Vulnerabilidad")
                            .append("</td>");
                    sb.append("<td>")
                            .append("Gravedad")
                            .append("</td>");
                    sb.append("<td>")
                            .append("Publicación")
                            .append("</td>");
                    sb.append("<td>")
                            .append("Software Afectado")
                            .append("</td>");
                    sb.append("<td>")
                            .append("Grupos Afectados")
                            .append("</td>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    for (Vulnerabilidad vuln : resultados) {
                        sb.append("<tr>");
                        sb.append("<td>")
                                .append(vuln.getIdVulnerabilidad())
                                .append("</td>");
                        sb.append("<td>")
                                .append(vuln.getSeveridad())
                                .append("</td>");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sb.append("<td>")
                                .append(sdf.format(vuln.getFechaPublicacion()))
                                .append("</td>");
                        sb.append("<td>");
                        sb.append("<table class='innergrid'>");
                        for (Software sw : vuln.getListaSoftware()) {
                            sb.append("<tr>");
                            sb.append("<td>").append(sw.getNombre()).append("<td>");
                            sb.append("</tr>");
                        }
                        sb.append("</table>");
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append("<table class='innergrid'>");
                        List<Software> afectado = vuln.getListaSoftware();
                        List<Grupo> grupos = vdao.obtenerGrupos(afectado);
                        for (Grupo grupo : grupos) {
                            sb.append("<tr>");
                            sb.append("<td>").append(grupo.getNombre()).append("<td>");
                            sb.append("</tr>");
                        }
                        sb.append("</table>");
                        sb.append("</td>");
                        sb.append("</tr>");
                    }
                    sb.append("</tbody>");
                    sb.append("</table>");
                    out.print(sb.toString());
                }
            }
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
