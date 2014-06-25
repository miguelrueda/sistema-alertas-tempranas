package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.org.banxico.sisal.dao.SourcesDAO;
import mx.org.banxico.sisal.entities.AppSource;

public class ConfigurationServlet extends HttpServlet implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ConfigurationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.getParameter("action").equals("")) {
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
                        List<AppSource> fuentes = dao.retrieveAll();
                        int noOfRecords = dao.getNoFuentes();
                        request.setAttribute("fuentes", fuentes);
                        request.setAttribute("noOfRecords", noOfRecords);
                        LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/sources.jsp");
                        nextJSP = "/admin/configuration/sources.jsp";
                        break;
                        /*
                    case 2:
                        LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/lists.jsp");
                        nextJSP = "/admin/configuration/lists.jsp";
                        break;*/
                }
                view = getServletContext().getRequestDispatcher(nextJSP);
                view.forward(request, response);
            }
            if (action.equalsIgnoreCase("edit")) {
                String tipo = (String) request.getParameter("tipo");
                int tipoI = Integer.parseInt(tipo);
                LOG.log(Level.INFO, "Petici\u00f3n Edit de Tipo: {0}", tipoI);
                switch (tipoI) {
                    case 1: //Editar fuente
                        LOG.log(Level.INFO, "Procesando Petición de Edición");
                        response.setContentType("text/plain");
                        response.setCharacterEncoding("UTF-8");
                        String id = (String) request.getParameter("idf");
                        String name = (String) request.getParameter("namef");
                        String url = (String) request.getParameter("urlf");
                        boolean flag = dao.editarFuente(Integer.parseInt(id), name, url);
                        if (flag) {
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                        break;
                }
            }
            if (action.equalsIgnoreCase("download")) {
                String url = (String) request.getParameter("url");
                LOG.log(Level.INFO, "Petici\u00f3n Download de URL: {0}", url);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                boolean flag = dao.descargarFuente(url);
                if (flag) {
                    response.getWriter().write("OK");
                } else {
                    response.getWriter().write("ERROR");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
