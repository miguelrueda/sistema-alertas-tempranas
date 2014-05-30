package jsf.mgbns;

import jsf.services.SourcesService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.SaFuentes;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class SourcesBean implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(SourcesBean.class.getName());
    private List<SaFuentes> sourcesList; 
    private String text = "Source1";
    @ManagedProperty("#{sourcesService}")
    private SourcesService service;
    
    @PostConstruct
    public void init() {
        sourcesList = service.obtenerFuentes();
    }

    public SourcesBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        LOG.log(Level.INFO, "Nuevo Valor: {0}", text);
        this.text = text;
    }

    public List<SaFuentes> getSourcesList() {
        return sourcesList;
    }

    public void setService(SourcesService service) {
        this.service = service;
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Fuente editada", ((SaFuentes) event.getObject()).getFntName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((SaFuentes) event.getObject()).getFntName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New: " + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   
}