package org.banxico.ds.sisal.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.banxico.ds.sisal.dao.GruposDAO;
import org.banxico.ds.sisal.dao.SourcesDAO;
import org.banxico.ds.sisal.entities.FuenteApp;
import org.banxico.ds.sisal.entities.Grupo;

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
        //Solicitar el parametro action
        String action = (String) request.getParameter("action");
        //Atributos del paginador
        int page = 1;
        int recordsPerPage = 20;
        String nextJSP = "/admin/Index.html";
        PrintWriter out = response.getWriter();
        //Instancia del request dispatcher
        RequestDispatcher view;
        //Instanciar el dao de fuentes y guardarlo en sesion
        SourcesDAO dao = new SourcesDAO();
        GruposDAO gdao = new GruposDAO();
        ServletContext context = this.getServletContext();
        HttpSession sesion = request.getSession();
        sesion.setAttribute("sourcesdao", dao);
        sesion.setAttribute("gruposdao", gdao);
        //Si el parametro es vista solicitar el siguiente parametro - Tipo
        if (action.equalsIgnoreCase("view")) {
            String tipo = (String) request.getParameter("tipo");
            int tipoI = Integer.parseInt(tipo);
            //Si el tipo es 1 acceder a las fuentes 2, a los grupos
            switch (tipoI) {
                case 1:
                    //Obtener la lista de fuentes y la cantidad
                    List<FuenteApp> fuentes = dao.obtenerFuentes();
                    int noOfRecords = dao.obtenerNumeroFuentes();
                    //Si no esta vacio establecerlos como atributos y redireccionar a la vista de fuentes
                    if (!fuentes.isEmpty() && noOfRecords > 0) {
                        request.setAttribute("fuentes", fuentes);
                        request.setAttribute("noOfRecords", noOfRecords);
                        nextJSP = "/admin/configuration/sources.jsp";
                    } else {
                        //redirección a error
                        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "No se encontro conexion con la BD");
                        nextJSP = "/sisalbm/admin/error.jsp";
                    }
                    break;
                case 2:
                    if (request.getParameter("page") != null) {
                        page = Integer.parseInt(request.getParameter("page"));
                    }
                    //Obtener la lista de grupos
                    List<Grupo> listaGrupos = gdao.retrieveGroupsFromLimit((page - 1) * recordsPerPage, recordsPerPage);
                    int grnoOfRecords = gdao.obtenerNumeroGrupos();
                    int grnoOfPages = (int) Math.ceil(grnoOfRecords * 1.0 / recordsPerPage);
                    request.setAttribute("listaGrupos", listaGrupos);
                    request.setAttribute("grnoOfPages", grnoOfPages);
                    request.setAttribute("currentPage", page);
                    nextJSP = "/admin/configuration/groups.jsp";
                    break;
            }
            //Realizar la redirección al recurso establecido
            view = getServletContext().getRequestDispatcher(nextJSP);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("edit")) {
            //Si el tipo es edit realizar una edición
            String tipo = (String) request.getParameter("tipo");
            int tipoI = Integer.parseInt(tipo);
            //Si el tipo es 1 editar la fuente
            switch (tipoI) {
                case 1: //Editar fuente
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    //Solicitar los parametros id, name y url
                    String id = (String) request.getParameter("idf");
                    String name = (String) request.getParameter("namef").trim();
                    String url = (String) request.getParameter("urlf").trim();
                    //Validar los parametros y devolver la respuesta adecuada
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
            //Si la petición es descarga
        } else if (action.equalsIgnoreCase("download")) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            //Obtener el path real de la aplicación
            //String realPath = context.getRealPath(File.separator);
            //Crear una instancia de path que apunte a la dirección obtenida
            //Path path = Paths.get(realPath);
            //LOG.log(Level.INFO, "El path real es: {0}", realPath);
            //Obtener la raiz del directorio y el subdirectorio donde se guardara el archivo
            //String root = path.getRoot().toString();
            //LOG.log(Level.INFO, "El directorio raiz es: {0}", root);
            //String subdir = path.subpath(0, 2).toString();
            //LOG.log(Level.INFO, "El subdirectorio es: {0}", subdir);
            //LOG.log(Level.INFO, "El archivo se guardara en: {0}{1}{2}vulnerabilidades{3}nombredearchivo.xml", new Object[]{root, subdir, File.separator, File.separator});
            //String saveDir = root + subdir + File.separator + "vulnerabilidades" + File.separator;
            //Solicitar el parametro ID y url
            String id = (String) request.getParameter("id");
            String url = (String) request.getParameter("url");
            //Obtener la ultima fecha de actualización
            Date regDate = dao.obtenerFechaActualizacion(id);
            Date now = new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            //Si las fechas son iguales la fuentes esta actualizada
            //LOG.log(Level.INFO, "Comparando las fechas: {0} y {1}", new Object[]{fmt.format(now), fmt.format(regDate)});
            if (fmt.format(now).equals(fmt.format(regDate))) {
                response.getWriter().write("UPDATED");
                //En otro caso se requiere actualizar
            } else {
                //Descargar la fuente
                boolean flag = dao.descargarFuente(id, url);
                if (flag) {
                    response.getWriter().write("OK");
                } else {
                    response.getWriter().write("ERROR");
                }
            }
        } else if (action.equalsIgnoreCase("addGroup")) {
            String nombre = request.getParameter("nombre");
            String tipo = request.getParameter("tipo");
            String categoria = null;
            if (tipo.equalsIgnoreCase("existente")) {
                categoria = request.getParameter("categoria");
            } else if (tipo.equalsIgnoreCase("nueva")) {
                categoria = request.getParameter("nuevacat");
            }
            String keys = request.getParameter("producto");
            StringTokenizer st = new StringTokenizer(keys, "[,]");
            int ntokens = st.countTokens();
            Integer [] llaves = new Integer[ntokens];
            int i = 0;
            while (st.hasMoreTokens()) {
                llaves[i] = Integer.parseInt(st.nextToken().replace("\"", "").replace("\\r\\n", ""));
                i++;
            }
            boolean created = false;
            try {
                created = gdao.crearGrupo(nombre, categoria, llaves);
            } catch (SQLException e) {
                LOG.log(Level.INFO, "ConfigurationController#AddGroup - Ocurrio un error al crear el grupo: {0}", e.getMessage());
            }
            if (created) {
                out.print("OK");
            } else if (!created) {
                out.print("ERROR");
            } else {
                out.print("UNKNOWN");
            }
        }
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
        Pattern urlPattern = Pattern.compile("((https?|ftp|file):((//)|(\\\\\\\\))+[\\\\w\\\\d:#@%/;$()~_?\\\\+-=\\\\\\\\\\\\.&]*)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(url);
        return matcher.find();
    }

}

