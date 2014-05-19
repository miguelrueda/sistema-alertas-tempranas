package jsf.managedbeans.vista;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@RequestScoped
public class UploadController {
    
    private UploadedFile uploaded;

    public UploadController() {
    }

    public UploadedFile getUploaded() {
        return uploaded;
    }

    public void setUploaded(UploadedFile uploaded) {
        this.uploaded = uploaded;
    }
    
    public void upload() {
        if (uploaded != null) {
            FacesMessage fMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "File: " + uploaded.getFileName() + " uploaded succesfully!", "Detail - OK");
            FacesContext.getCurrentInstance().addMessage(null, fMsg);
        }
    }
    
}
