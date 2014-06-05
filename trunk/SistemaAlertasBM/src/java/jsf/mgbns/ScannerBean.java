package jsf.mgbns;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.Scan;
import org.primefaces.event.FlowEvent;

@ManagedBean
@SessionScoped
public class ScannerBean implements java.io.Serializable {

    private boolean skip;
    private Scan scan;

    public ScannerBean() {
    }

    public void save() {
        FacesMessage msg = new FacesMessage("Succesful", "Welcome: Test");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

}
