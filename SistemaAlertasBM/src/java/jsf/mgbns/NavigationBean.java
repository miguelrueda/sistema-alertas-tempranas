package jsf.mgbns;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements java.io.Serializable {
    
    private static final long serialVersionUID = -41507L;
    private static final Logger LOG = Logger.getLogger(NavigationBean.class.getName());
    
    public NavigationBean() {
    }
    
    public String redirectToLogin() {
        LOG.log(Level.SEVERE, "Redireccionando a Login");
        return "/Login.xhtml?faces-redirect=true";
    }
    
    public String toLogin() {
        LOG.log(Level.SEVERE, "Moviendo a Login");
        return "/Login.xhtml";
    }
    
    public String redirectToWelcome() {
        LOG.log(Level.SEVERE, "Redireccionando al Inicio");
        //return "/secured/welcome.xhtml?faces-redirect=true";
        return "/secured/vulnerabilities/vulnUpdates.xhtml?faces-redirect=true";
    }
    
    public String toWelcome() {
        LOG.log(Level.SEVERE, "Moviendo al Inicio");
        return "/secured/welcome.xhtml";
    }
    
}