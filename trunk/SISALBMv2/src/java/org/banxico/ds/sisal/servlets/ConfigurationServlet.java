package org.banxico.ds.sisal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.banxico.ds.sisal.dao.SourcesDAO;
import org.banxico.ds.sisal.entities.FuenteApp;
import org.banxico.ds.sisal.entities.Software;

/**
 * Controlador para las fuentes y listas de sw
 *
 * @author t41507
 * @version 04072014
 */
public class ConfigurationServlet extends HttpServlet implements java.io.Serializable {

    /**
     * Atributos de serialización y logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ConfigurationServlet.class.getName());

    /**
     * Método doGet para resolver las peticiones
     *
     * @param request referencia de solicitud
     * @param response referencia de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //if (!request.getParameter("action").equals("")) {
        String action = (String) request.getParameter("action");
        LOG.log(Level.INFO, "Procesando petici\u00f3n de configuraci\u00f3n de tipo: {0}", action);
        String nextJSP = "/admin/Index.html";
        RequestDispatcher view;
        SourcesDAO dao = new SourcesDAO();
        HttpSession sesion = request.getSession();
        sesion.setAttribute("sourcesdao", dao);

        if (action.equalsIgnoreCase("view")) {
            //¿Solicitar el siguiente parametro?
            String tipo = (String) request.getParameter("tipo");
            int tipoI = Integer.parseInt(tipo);
            switch (tipoI) {
                case 1:
                    List<FuenteApp> fuentes = dao.obtenerFuentes();
                    int noOfRecords = dao.getNoFuentes();
                    if (!fuentes.isEmpty() && noOfRecords > 0) {
                        request.setAttribute("fuentes", fuentes);
                        request.setAttribute("noOfRecords", noOfRecords);
                        LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/sources.jsp");
                        nextJSP = "/admin/configuration/sources.jsp";
                    } else {
                        LOG.log(Level.INFO, "Redireccionando a pagina de error: /error.jsp");
                        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "No se encontro conexion con la BD");
                        nextJSP = "/sisalbm/admin/error.jsp";
                    }
                    break;
                case 2:
                    LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/groups.jsp");
                    nextJSP = "/admin/configuration/groups.jsp";
                    break;
            }
            view = getServletContext().getRequestDispatcher(nextJSP);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("edit")) {
            String tipo = (String) request.getParameter("tipo");
            int tipoI = Integer.parseInt(tipo);
            LOG.log(Level.INFO, "Petici\u00f3n Edit de Tipo: {0}", tipoI);
            switch (tipoI) {
                case 1: //Editar fuente
                    LOG.log(Level.INFO, "Procesando Petición de Edición");
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    String id = (String) request.getParameter("idf");
                    String name = (String) request.getParameter("namef").trim();
                    String url = (String) request.getParameter("urlf").trim();
                    LOG.log(Level.INFO, "Edici\u00f3n - Valores Recibidos: {0}/{1}/{2}", new Object[]{id, name, url});
                    boolean flag = false;
                    if (name.length() > 5 && validateURL(url)) {
                        flag = dao.editarFuente(Integer.parseInt(id), name, url);
                        if (flag) {
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                    } else {
                        if (name.length() < 5) {
                            response.getWriter().write("nombre");
                        } else {
                            response.getWriter().write("url");
                        }
                    }
                    break;
            }
        } else if (action.equalsIgnoreCase("download")) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            String id = (String) request.getParameter("id");
            LOG.log(Level.INFO, "Actualizar la fuente: {0}", id);
            String url = (String) request.getParameter("url");
            LOG.log(Level.INFO, "Descargar la url: {0}", url);
            Date regDate = dao.obtenerFechaActualizacion(id);
            Date now = new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            LOG.log(Level.INFO, "Fecha del registro: {0}/Fecha actual:{1}", new Object[]{fmt.format(regDate), fmt.format(now)});
            LOG.log(Level.INFO, "{0}", fmt.format(now).equals(fmt.format(regDate)));
            if (fmt.format(now).equals(fmt.format(regDate))) {
                LOG.log(Level.INFO, "Fuente actualizada: {0}", id);
                response.getWriter().write("UPDATED");
            } else {
                //response.getWriter().write("NOT UPDATED");
                boolean flag = dao.descargarFuente(id, url);
                if (flag) {
                    LOG.log(Level.INFO, "Descargar Fuente: {0}", id);
                    response.getWriter().write("OK");
                } else {
                    LOG.log(Level.INFO, "Error en descarga: {0}", url);
                    response.getWriter().write("ERROR");
                }
            }
        } 
        //}
    }

    /**
     * Método doPost
     *
     * @param req referencia de solicitud
     * @param resp referencia de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Métod que se encarga de validar la URL introducida por el usuario
     *
     * @param url cadena con la url a validar
     * @return boleano con la condición de validez
     */
    private boolean validateURL(String url) {
        Pattern urlPattern = Pattern.compile("((https?|ftp|file):((//)|(\\\\\\\\))+[\\\\w\\\\d:#@%/;$()~_?\\\\+-=\\\\\\\\\\\\.&]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(url);
        return matcher.find();
    }

}
