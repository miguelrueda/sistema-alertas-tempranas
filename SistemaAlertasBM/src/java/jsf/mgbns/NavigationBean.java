package jsf.mgbns;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements java.io.Serializable {
    
    private static final long serialVersionUID = -41507L;

    public NavigationBean() {
    }
    
    public String redirectToLogin() {
        return "/Login.xhtml?faces-redirect=true";
    }
    
    public String toLogin() {
        return "/Login.xhtml";
    }
    
    public String redirectToWelcome() {
        return "/secured/welcome.xhtml?faces-redirect=true";
    }
    
    public String toWelcome() {
        return "/secured/welcome.xhtml";
    }
    
}