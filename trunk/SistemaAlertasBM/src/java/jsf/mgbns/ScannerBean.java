package jsf.mgbns;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.Producto;
import jpa.entities.Scan;
import org.primefaces.event.FlowEvent;

@ManagedBean
@SessionScoped
public class ScannerBean implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());

    private boolean skip;
    private Scan scan;
    private List<Producto> productsList;
    private boolean todaslasListas;
    private Integer tipo;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private boolean escaneoTerminado = false;
    private String temp;

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

    public List<Producto> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Producto> productsList) {
        this.productsList = productsList;
    }

    public void onTypeChange() {
        System.out.println("Handler event fired!");
        System.out.println("showPanelBool = ");
    }

    public boolean isTodaslasListas() {
        return todaslasListas;
    }

    public void setTodaslasListas(boolean todaslasListas) {
        this.todaslasListas = todaslasListas;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void addMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Check seleccionado"));
    }

    public void ejecutar() {
        //doScan();
        int i = 0;
        try {
            for (int x = 0; x < 10; x++) {
                Thread.sleep(500);
                LOG.log(Level.INFO, "Durmiendo: {0}", 500);
                i += 100;
                if (i == 1000) {
                    escaneoTerminado = true;
                    this.temp = "ESCANEO TERMINADO\n A continuación se listan los resultados:";
                    LOG.log(Level.INFO, "Escaneo Terminado");
                }
            }
        } catch (InterruptedException e) {
            LOG.log(Level.INFO, "Excepcion: {0}", e.getMessage());
        }
    }

    private Integer progress;

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + (int) (Math.random() * 35);
            if (progress > 100) {
                progress = 100;
                escaneoTerminado = true;
            }
        }
        return progress;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Escaneo terminado"));
    }

    public void cancel() {
        progress = null;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public boolean isEscaneoTerminado() {
        return escaneoTerminado;
    }

    public void setEscaneoTerminado(boolean escaneoTerminado) {
        this.escaneoTerminado = escaneoTerminado;
    }

}
