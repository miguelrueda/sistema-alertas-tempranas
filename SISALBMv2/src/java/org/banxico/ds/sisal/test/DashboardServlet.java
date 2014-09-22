package org.banxico.ds.sisal.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Grupo;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.entities.Vulnerabilidad;
import org.banxico.ds.sisal.entities.VulnerabilidadReferencia;
import org.banxico.ds.sisal.scanner.ScannerBean;

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
                    Set<Vulnerabilidad> diferentes = new HashSet<Vulnerabilidad>();
                    Set<Vulnerabilidad> duplicados = new HashSet<Vulnerabilidad>();
                    for (Vulnerabilidad vulnerabilidad : resultados) {
                        if (diferentes.contains(vulnerabilidad)) {
                            duplicados.add(vulnerabilidad);
                        } else {
                            diferentes.add(vulnerabilidad);
                        }
                    }
                    List<Vulnerabilidad> order = new ArrayList<Vulnerabilidad>();
                    order.addAll(diferentes);
                    Collections.sort(order);
                    resultados = new ArrayList<Vulnerabilidad>();
                    resultados.addAll(order);
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
                    sb.append("<td>")
                            .append("Detalles")
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
                        List<Grupo> grupos = vdao.obtenerGruposPorVulnerabilidad(vuln.getIdVulnerabilidad());//.obtenerGrupos(afectado);
                        for (Grupo grupo : grupos) {
                            sb.append("<tr>");
                            sb.append("<td>").append(grupo.getNombre()).append("<td>");
                            sb.append("</tr>");
                        }
                        sb.append("</table>");
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append("<a href='#' class='view' onclick='verDetalle(\"")
                                .append(vuln.getIdVulnerabilidad())
                                .append("\");'>")
                                .append("<img src='../resources/images/search.png' alt='magni' id='tableicon' />")
                                .append("</a>");
                        sb.append("</td>");
                        sb.append("</tr>");
                    }
                    sb.append("</tbody>");
                    sb.append("</table>");
                    out.print(sb.toString());
                } else if (action.equalsIgnoreCase("retlabels")) {
                    String[] labels = {"Apple", "Microsoft", "IBM", "Oracle"};
                    StringBuilder json = new StringBuilder();
                    out.println(Arrays.toString(labels));
                } else if (action.equalsIgnoreCase("retrieveby")) {
                    String nombre = request.getParameter("nombre");
                    sb = new StringBuilder();
                    generarRespuesta(vdao, nombre, sb);
                    out.print(sb.toString());
                } else if (action.equalsIgnoreCase("genchart")) {
                    HashMap<String, Integer> datos = vdao.obtenerEstadisticasFabricantes();
                    TreeMap sorted = new TreeMap(datos);
                    String json = "";
                    int res = 0;
                    Iterator<String> iterator = sorted.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        if (datos.get(key) > 50) {
                            res += datos.get(key);
                        }
                        json += "{}";
                    }
                    response.setContentType("application/json");
                    response.getWriter().write(json.toString());
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

    private void generarRespuesta(VulnerabilityDAO vdao, String nombre, StringBuilder sb) {
        Vulnerabilidad v = vdao.obtenerVulnerabilidadPorNombre(nombre);
        sb.append("<div class='datagrid' id='dataexport'>");
        sb.append("<table id='tableexport' border='1'>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<td class='vulnHeader' colspan='2'>")
                .append(nombre)
                .append("</td>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>Fecha de Publicación:</td>");
        sb.append("<td id='pubdate'>")
                .append(v.getFechaPublicacion())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Fecha de Modificación:</td>");
        sb.append("<td id='moddate'>")
                .append(v.getFechaModificacion())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Descripción:</td>");
        sb.append("<td id='desc'>")
                .append(v.getDescripcion());
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Gravedad:</td>");
        sb.append("<td id='criticidad'>")
                .append(v.getSeveridad())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>(CVSS) Calificación:</td>");
        sb.append("<td id='cvsscore'>")
                .append(v.getCalificacionCVSS())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>(CVSS) Vector de Ataque:</td>");
        sb.append("<td>");
        sb.append(vdao.describirVector(v.getVectorCVSS()));
        sb.append("</td>");
        sb.append("</tr>");
        List<VulnerabilidadReferencia> referencias = vdao.obtenerReferencias(nombre);

        sb.append("<tr>");
        sb.append("<td>Referencias:</td>");
        sb.append("<td id='refs'>");
        sb.append("<table style='border: currentColor; border-image: none;'>");
        sb.append("<tbody>");
        for (VulnerabilidadReferencia ref : referencias) {
            sb.append("<tr>");
            sb.append("<td>")
                    .append(ref.getFuente())
                    .append("</td>");
            sb.append("<td>")
                    .append(ref.getUrl())
                    .append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td>Software Vulnerable:</td>");
        sb.append("<td>");
        sb.append("<table style='border: currentColor; border-image: none;'>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<td>Proveedor</td>");
        sb.append("<td>Producto</td>");
        sb.append("<td>Versión(es)</td>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        List<Software> vulnsoft = vdao.obtenerSoftwareVulnerable(nombre);
        for (Software soft : vulnsoft) {
            sb.append("<tr>");
            sb.append("<td>").append(soft.getFabricante()).append("</td>");
            sb.append("<td>").append(soft.getNombre()).append("</td>");
            sb.append("<td>").append(soft.getVersion()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td>Grupos Afectados:</td>");
        sb.append("<td>");
        sb.append("<table style='border: currentColor; border-image: none;'>");
        sb.append("<tbody>");
        List<Grupo> grupos = vdao.obtenerGruposPorVulnerabilidad(nombre);
        for (Grupo group : grupos) {
            sb.append("<tr>");
            sb.append("<td>").append(group.getNombre()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");

        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</div>");
    }

}
