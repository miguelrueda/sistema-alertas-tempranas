package jsf.mgbns;

import ejb.model.SourcesEJB;
import jsf.services.SourcesService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.SaFuentes;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 * BEAN que se encarga de administrar las fuentes que alimentan el contenido del
 * sistema
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean
@SessionScoped
public class SourcesBean implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(SourcesBean.class.getName());
    private SaFuentes selectedSource;
    /**
     * Atributos principales del BEAN
     */
    private List<SaFuentes> sourcesList;
    /**
     * Inyección del Servicio de fuentes
     */
    @ManagedProperty("#{sourcesService}")
    private SourcesService service;
    @EJB
    private SourcesEJB sourcesEJB;

    /**
     * Mét odo de inicialización del bean
     */
    @PostConstruct
    public void init() {
        sourcesList = service.obtenerFuentes();
    }

    /**
     * Constructor sin parámetros
     */
    public SourcesBean() {
    }

    /**
     * Mét odo para obtener la lista de fuentes
     *
     * @return List con las fuentes almacenadas
     */
    public List<SaFuentes> getSourcesList() {
        return sourcesList;
    }

    /**
     * Mét odo setter para el servicio de fuentes
     *
     * @param service Servicio del tipo SourcesService
     */
    public void setService(SourcesService service) {
        this.service = service;
    }

    /**
     * Mét odo para editar las filas de la tabla en la vista
     *
     * @param event de tipo RowEdit
     */
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Fuente editada", ((SaFuentes) event.getObject()).getFntName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Mét odo para cancelar la edición de una fila
     *
     * @param event de tipo RowEdit
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((SaFuentes) event.getObject()).getFntName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Mét odo para cambiar los valores de la fila seleccionada
     *
     * @param event de tipo CellEdit TODO: Cambiarlo, agregar la parte de
     * persistencia con EM
     */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New: " + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public SaFuentes getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(SaFuentes selectedSource) {
        this.selectedSource = selectedSource;
    }

    public void updateFile() {
        LOG.log(Level.INFO, "* * * * * * * * * ");
        FacesMessage fmsg = null;
                
        boolean flag = sourcesEJB.descargarActualizaciones(sourcesList);
        if (flag) {
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Descargando actualizaciones", 
                    "Descargando actualizaciones de las URL");
            
        } else {
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la descarga", "Ocurrio un error al intentar actualizar las fuentes");
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
        LOG.log(Level.INFO, "* * * * * * * * * ");
    }

}
