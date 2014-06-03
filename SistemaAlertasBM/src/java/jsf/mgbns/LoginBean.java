package jsf.mgbns;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());
    //TEMP Cambiar por Servicio
    private static final String[] users = {"t41507:Banxico1", "admin:admin"};
    private String username;
    private String password;
    private boolean loggedIn = false;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public String doLogin() {
        for (String user : users) {
            String dbUsername = user.split(":")[0];
            String dbPassword = user.split(":")[1];
            if (dbUsername.equals(username) && dbPassword.equals(password)) {
                loggedIn = true;
                LOG.log(Level.INFO, "Iniciando sesion . . . - Redireccionando a Welcome");
                return navigationBean.redirectToWelcome();
            }
        }
        FacesMessage msg = new FacesMessage("Error", "El nombre de usuario o contraseña son incorrectos.");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        LOG.log(Level.SEVERE, "Error de Inicio de Sesión - Redireccion a Login");
        return navigationBean.toLogin();
    }

    public String doLogout() {
        loggedIn = false;
        LOG.log(Level.SEVERE, "Cerrando Sesion - Redireccionando a Login");
        //FacesMessage msg = new FacesMessage("Exito!", "La sesión fue cerrada exitosamente.");
        //msg.setSeverity(FacesMessage.SEVERITY_INFO);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return navigationBean.redirectToLogin();
    }

}

/*
 REFERENCIAS
 http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 http://www.jroller.com/hasant/entry/jsf_logout_and_redirect_user
 http://javaknowledge.info/authentication-based-secure-login-logout-using-jsf-2-0-and-primefaces-3-4-1/
 http://todoenjava.blogspot.mx/2013/12/jsf-login-y-sesiones.html
 */
