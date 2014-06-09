package jsf.mgbns;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean que permite manejar la nevagación en las vistas del sistema
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean
@SessionScoped
public class NavigationBean implements java.io.Serializable {
    
    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -41507L;
    private static final Logger LOG = Logger.getLogger(NavigationBean.class.getName());
    
    /**
     *  Constructor sin parámetros
     */
    public NavigationBean() {
    }
    
    /**
     * Mét odo que se encarga de hacer la redirección a Login
     *
     * @return String con la dirección de Login
     */
    public String redirectToLogin() {
        LOG.log(Level.SEVERE, "Redireccionando a Login");
        return "/Login.xhtml?faces-redirect=true";
    }
    
    /**
     * Mét odo que desplaza la vista hacia Login
     *
     * @return String con la dirección de Login
     */
    public String toLogin() {
        LOG.log(Level.SEVERE, "Moviendo a Login");
        return "/Login.xhtml";
    }
    
    /**
     * Mét odo que realiza la redirección a la pagina de Inicio
     *
     * @return String con la dirección del recurso establecido como Inicio
     */
    public String redirectToWelcome() {
        LOG.log(Level.SEVERE, "Redireccionando al Inicio");
        //return "/secured/welcome.xhtml?faces-redirect=true";
        return "/secured/vulnerabilities/vulnUpdates.xhtml?faces-redirect=true";
    }
    
    /**
     * Mét odo que desplaza la vista hacia la pagina de Inicio
     *
     * @return String con la dirección del recurso establecido como Inicio
     */
    public String toWelcome() {
        LOG.log(Level.SEVERE, "Moviendo al Inicio");
        return "/secured/welcome.xhtml";
    }
    
    /**
     * Mét odo que realiza la redirección a la pagina de resultados
     *
     * @return String con la dirección del recurso de Resultados
     */
    public String redirectToResults() {
        LOG.log(Level.INFO, "Redireccionando a los resultados");
        return "/secured/scanner/scanResults.xhtml?faces-redirect=true";
    }
    
}