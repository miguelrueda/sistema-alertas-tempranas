package jsf.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsf.mgbns.LoginBean;

/**
 * Filtro para verificar la validez de la sesión
 *
 * @author t41507
 * @version 09.06.2014
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/secured/*"})
public class LoginFilter implements Filter {

    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(LoginFilter.class.getName());

    /**
     * Mét odo sobrecargado de inicialización
     *
     * @param filterConfig configuración del filtro
     * @throws ServletException cuando ocurre una excepción
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Mét odo sobrecargado doFilter para llevar a cabo el filtro dentro de la
     * aplicación
     *
     * @param request de tipo Servlet para llevar a cabo la solicitud
     * @param response de tipo Servlet para llevar a cabo la respuesta
     * @param chain de tipo FilterChain para volver a ejecutar el filtro con otros recursos
     * @throws IOException Cuando ocurre excepción de entrada o salida del recurso
     * @throws ServletException cuando ocurre una excepción con solicitud o respuesta
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Obtener el loginBean a partir de la sesion
        HttpServletRequest req = (HttpServletRequest) request;
        LoginBean loginBean = (LoginBean) req.getSession().getAttribute("loginBean");
        LOG.log(Level.SEVERE, "Obteniendo referencia a login");
        /*
         Para la primer solicitud de la aplicacion no hay loginBean en la sesion, entonces el cliente necesita loggearse
         Para otras solicitudes loginBean esta presente pero necesitamos chcar si el usuario se ha logeado
         */
        /*
         if (loginBean == null || !loginBean.isLoggedIn()) {
         LOG.log(Level.SEVERE, "EL usuario no esta autenticado.");
         String contextPath = ((HttpServletRequest) request).getContextPath();
         LOG.log(Level.SEVERE, "Contexto: {0}", contextPath);
         ((HttpServletResponse) response).sendRedirect(contextPath + "/Login.xhtml");
         } else {
         LOG.log(Level.SEVERE, "Usuario Autenticado - OK/Cambiando al recurso solicitado");
         chain.doFilter(request, response);
         }*/
        try {
            if (loginBean != null && loginBean.isLoggedIn()) {
                //El usuario esta logeado --> Completar la solicitud
                LOG.log(Level.INFO, "Usuario Autenticado -> Completando Solicitud.");
                chain.doFilter(request, response);
            } else {
                HttpServletResponse resp = (HttpServletResponse) response;
                LOG.log(Level.INFO, "Usuario No Autenticado -> Redireccionando al inicio ");
                LOG.log(Level.INFO, "Contexto: {0}", req.getContextPath());
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/Login.xhtml");

            }
        } catch (IOException | ServletException e) {
            LOG.log(Level.WARNING, "Problema -->{0}", e.getMessage());
        }
    }

    /**
     *  Mét odo destroy() sobrecargado
     */
    @Override
    public void destroy() {
    }

}
