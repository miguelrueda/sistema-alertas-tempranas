package jsf.mgbns;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Bean que permite manejar la logica para el inicio de sesión y manejar las 
 * redirecciones hacia los recursos solicitados.
 *
 * @author t41507
 * @version 09.06.2014
 * En esta versión no se tiene acceso a datos en una BD, en esta versión se
 * realiza por medio de una simulación utilizando un arreglo de datos.
 */
@ManagedBean
@SessionScoped
public class LoginBean implements java.io.Serializable {
    
    /**
     * ATRIBUTOS de serialización y logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());
    /**
     * TODO: El valor de users es temporal debe cambiarse por un servicio
     */
    private static final String[] users = {"t41507:Banxico1", "admin:admin"};
    
    /**
     * Atributos principales de la clase
     */
    private String username;
    private String password;
    private boolean loggedIn = false;
    /**
     * Inyección del Bean de navegación
     */
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     *  Constructor sin parametros de la clase LoginBean
     */
    public LoginBean() {
    }

    /**
     * Met odo getUsername para obtener el nombre del usuario y presentarlo en 
     * el apartado correspondiente
     *
     * @return String con el nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Met odo setUsername para establecer el nombre del usuario en el formulario
     * de login
     *
     * @param username String con el nombre del usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Met odo getPassword para obtener el password del usuario
     * ¿Cifrarlo?
     *
     * @return String con el password del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Met odo setPassword para establecer el password del usuario para su posterior
     * validación
     *
     * @param password String con el password del Usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Met odo que devuelve el valor de la bandera de logueo
     *
     * @return true si esta logueado, false si no lo está
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Mét odo que establece el valor de la bandera de logueo
     *
     * @param loggedIn boolean con true si se pudo loguear, false si hubo un error
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Mét odo setter para el bean de navegación, mét odo necesario para el 
     * funcionamiento de este bean
     *
     * @param navigationBean Bean de navegación de tipo NavigationBean
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Mét odo que se encarga de hacer el login del usuario al sistema
     *
     * @return String con la dirección del recurso al cual se va a acceder
     */
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

    /**
     * Mét odo que se encarga de cerrar la sesión para el usuario logueado
     *
     * @return String con la dirección del login.
     */
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
