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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsf.mgbns.LoginBean;

public class LoginFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Obtener el loginBean a partir de la sesion
        LoginBean loginBean = (LoginBean) ((HttpServletRequest) request).getSession().getAttribute("loginBean");
        LOG.log(Level.SEVERE, "Obteniendo referencia a login");
        /*
         Para la primer solicitud de la aplicacion no hay loginBean en la sesion, entonces el cliente necesita loggearse
         Para otras solicitudes loginBean esta presente pero necesitamos chcar si el usuario se ha logeado
         */
        if (loginBean == null || !loginBean.isLoggedIn()) {
            LOG.log(Level.SEVERE, "EL usuario no esta logeado");
            String contextPath = ((HttpServletRequest) request).getContextPath();
            LOG.log(Level.SEVERE, "Contexto: {0}", contextPath);
            ((HttpServletResponse) response).sendRedirect(contextPath + "/Login.xhtml");
        }
        LOG.log(Level.SEVERE, "Chain do Filter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
