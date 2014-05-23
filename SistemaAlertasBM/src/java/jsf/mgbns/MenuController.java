package jsf.mgbns;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author t41507
 * @version 
 */
@ManagedBean(name="menuController", eager=true)
@SessionScoped
public class MenuController {

    public MenuController() {
    }
    
    public void save() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Saving data ocurred succesfully!"));
    }
    
    public void update() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Updating data ocurred succesfully!"));
    }
    
    public void delete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted", "Deleting data ocurred succesfully!"));
    }
    
}