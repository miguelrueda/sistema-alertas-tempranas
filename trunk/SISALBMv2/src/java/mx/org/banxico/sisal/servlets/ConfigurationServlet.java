package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.org.banxico.sisal.dao.SourcesDAO;
import mx.org.banxico.sisal.entities.AppSource;

public class ConfigurationServlet extends HttpServlet implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ConfigurationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.getParameter("tipo").equals("")) {
            String tipo = (String) request.getParameter("tipo");
            LOG.log(Level.INFO, "Procesando petici\u00f3n de configuraci\u00f3n de tipo: {0}", tipo);
            String nextJSP = "/admin/Index.html";
            RequestDispatcher view;
            SourcesDAO dao = new SourcesDAO();
            switch (tipo) {
                case "1":
                    List<AppSource> fuentes = dao.retrieveAll();
                    int noOfRecords = dao.getNoFuentes();
                    request.setAttribute("fuentes", fuentes);
                    request.setAttribute("noOfRecords", noOfRecords);
                    LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/sources.jsp");
                    nextJSP = "/admin/configuration/sources.jsp";
                    break;
                case "2":
                    LOG.log(Level.INFO, "Redireccionando al recurso: /configuration/lists.jsp");
                    nextJSP = "/admin/configuration/lists.jsp";
                    break;
            }
            view = getServletContext().getRequestDispatcher(nextJSP);
            view.forward(request, response);
        } //if request tipo
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}