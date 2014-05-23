package jsf.mgbns;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author t41507
 * @version 23.05.2014
 */
@ManagedBean
@RequestScoped
public class BlockUIView {
    
    private String firstname;
    private String lastname;
    
    /**
     * Creates a new instance of BlockUIView
     */
    public BlockUIView() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public void save() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You've logged in"));
    }
    
}