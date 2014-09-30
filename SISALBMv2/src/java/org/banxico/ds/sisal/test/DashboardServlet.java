package org.banxico.ds.sisal.test;

import com.google.gson.JsonObject;
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
import java.text.DecimalFormat;
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

/**
 * Servlet que se encarga de manejar todas las peticiones que se realizan sobre
 * el dashboard
 *
 * @author t41507
 * @version 24.09.2014
 */
public class DashboardServlet extends HttpServlet {

    /**
     * Atributo de Serialización
     */
    private static final Logger LOG = Logger.getLogger(DashboardServlet.class.getName());

    /**
     * Método que se encarga de procesar las peticiones GET Y POST sobre el 
     * dashboard
     *
     * @param request referencia de la solicitud
     * @param response referencia de la respuesta
     * @throws ServletException excepción de servlet
     * @throws IOException excepción de flujos de entrada salida
     */
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
                } else if (action.equalsIgnoreCase("getchart")) {
                    String type = request.getParameter("type");
                    if (type.equalsIgnoreCase("bar")) {
                        HashMap<String, Integer> datos = vdao.obtenerEstadisticasFabricantes();
                        TreeMap sorted = new TreeMap(datos);
                        Iterator<String> iterator = sorted.keySet().iterator();
                        List<Object> barDataSet = new ArrayList<Object>();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if (datos.get(key) > 50) {
                                JsonObject temp = new JsonObject();
                                temp.addProperty("fabricante", key);
                                temp.addProperty("cuenta", datos.get(key));
                                barDataSet.add(temp);
                            }
                        }
                        //Gson gson = new Gson();
                        //String json = gson.toJson(barDataSet);
                        //response.setContentType("application/json");
                        //response.getWriter().write(json.toString());
                        out.println(barDataSet.toString());
                    } else if (type.equalsIgnoreCase("pie")) {
                        HashMap<String, Integer> datos = vdao.obtenerGravedadVulnerabilidades();
                        int total = 0;
                        Iterator<String> keysetIterator = datos.keySet().iterator();
                        while (keysetIterator.hasNext()) {
                            String llave = keysetIterator.next();
                            total += datos.get(llave);
                        }
                        HashMap<String, Double> piedata = calcularPorcentajes(datos, total);
                        ArrayList<Object> pieDataSet = new ArrayList<Object>();
                        keysetIterator = piedata.keySet().iterator();
                        while (keysetIterator.hasNext()) {
                            JsonObject temp = new JsonObject();
                            String key = keysetIterator.next();
                            temp.addProperty("value", piedata.get(key));
                            if (key.equalsIgnoreCase("altas")) {
                                temp.addProperty("color", "#FF4545");
                                temp.addProperty("title", "Vulnerabilidades Graves " + piedata.get(key) + "%");
                            } else if (key.equalsIgnoreCase("medias")) {
                                temp.addProperty("color", "#FFFF45");
                                temp.addProperty("title", "Vulnerabilidades Moderadas " + piedata.get(key) + "%");
                            } else if (key.equalsIgnoreCase("bajas")) {
                                temp.addProperty("color", "#69A469");
                                temp.addProperty("title", "Vulnerabilidades Bajas " + piedata.get(key) + "%");   
                            }
                            pieDataSet.add(temp);
                        }
                        out.println(pieDataSet.toString());
                    }
                }
            }
        } finally {
            out.close();
        }
    }

    /**
     * Método doGet
     *
     * @param req referencia de la solicitud
     * @param resp referencia de la respuesta
     * @throws ServletException excepción de servlet
     * @throws IOException excepción de flujos de entrada salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Método doPost
     *
     * @param req referencia de la solicitud
     * @param resp referencia de la respuesta
     * @throws ServletException excepción de servlet
     * @throws IOException excepción de flujos de entrada salida
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Método que se encarga de generar el contenido HTML para la vista del dashboard
     * 
     * @param vdao Referencia del dao de vulnerabilidades
     * @param nombre identificador de a vulnerabilidad
     * @param sb referencia del buffer
     */
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

    /**
     * Método que se encarga de retornar un mapa con los porcentajes de la gravedad
     * de las vulnerabilidades
     * 
     * @param datos Mapa con los datos para calcular los porcentajes
     * @param total Total de vulnerabilidades existentes
     * @return Mapa con los porcentajes calculados
     */
    private HashMap<String, Double> calcularPorcentajes(HashMap<String, Integer> datos, int total) {
        HashMap<String, Double> data = new HashMap<String, Double>();
        Iterator<String> keysetIterator = datos.keySet().iterator();
        while (keysetIterator.hasNext()) {
            String key = keysetIterator.next();
            Double porcentaje = ((datos.get(key).doubleValue() / total) * 100);
            DecimalFormat df = new DecimalFormat("#.##");
            data.put(key, Double.valueOf(df.format(porcentaje)));
        }
        return data;
    }

}