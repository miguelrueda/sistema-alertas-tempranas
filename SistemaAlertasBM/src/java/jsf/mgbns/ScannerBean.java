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

/**
 * Bean para realizar el escaneo dentro de la aplicación
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean
@SessionScoped
public class ScannerBean implements java.io.Serializable {

    /**
     * Atributos de serialización y logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());
    /**
     * Atributos principales del Bean
     */
    private Scan scan;
    private List<Producto> productsList;
    private boolean todaslasListas;
    private Integer tipoEscaneo;
    private boolean escaneoTerminado = false;
    private String temp;
    private Integer progress;
    /**
     * Inyección del bean de navegación
     */
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Constructor sin parametros
     */
    public ScannerBean() {
    }

    /**
     * Mét odo getter para el scan
     *
     * @return Scan
     */
    public Scan getScan() {
        return scan;
    }

    /**
     * Mét odo setter para el scan
     *
     * @param scan Scan
     */
    public void setScan(Scan scan) {
        this.scan = scan;
    }

    /**
     * Mét odo getter para la lista de productos
     *
     * @return List de productos seleccionada
     */
    public List<Producto> getProductsList() {
        return productsList;
    }

    /**
     * Mét odo setter para la lista de productos
     *
     * @param productsList List de productos
     */
    public void setProductsList(List<Producto> productsList) {
        this.productsList = productsList;
    }

    /**
     * Mét odo para comprobar la bandera de seleccion de todas las listas
     *
     * @return true si esta seleccionada, false en caso contrario
     */
    public boolean isTodaslasListas() {
        return todaslasListas;
    }

    /**
     * }
     * Mét odo para establecer la bandera de selección de todas las listas
     *
     * @param todaslasListas true si se establece, false en caso contrario
     */
    public void setTodaslasListas(boolean todaslasListas) {
        this.todaslasListas = todaslasListas;
    }

    /**
     * Mét odo getter para el tipo de escaneo
     *
     * @return 1 si es parcial, 2 si es completo ¿REVISAR?
     */
    public Integer getTipo() {
        return tipoEscaneo;
    }

    /**
     * Mét odo setter para el tipo de escaneop
     *
     * @param tipo 1 si es parcial, 2 si es completo ¿REVISAR?
     */
    public void setTipo(Integer tipo) {
        this.tipoEscaneo = tipo;
    }

    /**
     * Mét odo para agregar un mensaje en la vista ¿Puede funcionar como
     * escucha?
     */
    public void addMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Check seleccionado"));
    }

    /**
     * Mét odo para llevar a cabo el escaneo, TODO: Lo ideal será pasarlo a un
     * EJB, junto con el SCAN y llevarlo a cabo para esperar solamente el result
     * y presentarlo TODO: Buscar la forma de recrear la vista despues del
     * escaneo
     */
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

    /**
     * Mét odo getter para el progreso del escaneo TODO: Cambiarlo a un EJB
     *
     * @return Integer con el progreso
     */
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

    /**
     * Mét odo setter para la inyección del Bean de navegación
     *
     * @param navigationBean bean de navegación
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Mét odo setter para establecer el progreso en la vista
     *
     * @param progress Integer con el progreso del escaneo
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * Escucha para el fin del escaneo TODO: Cambiarlo a EJB
     */
    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Escaneo terminado"));
    }

    /**
     * Escucha para cancelar el escaneo
     */
    public void cancel() {
        progress = null;
    }

    /**
     * Getter para el valor de prueba temporal
     *
     * @return String con un valor temporal
     */
    public String getTemp() {
        return temp;
    }

    /**
     * Setter para el valor temporal
     *
     * @param temp String con un valor temporal
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * Getter para el valor de la bandera de escaneo terminado
     *
     * @return true si se termino, false en caso contrario
     */
    public boolean isEscaneoTerminado() {
        return escaneoTerminado;
    }

    /**
     * Setter para el valor de la bandera de escaneo terminado
     *
     * @param escaneoTerminado true si se termina, false en caso contrario
     */
    public void setEscaneoTerminado(boolean escaneoTerminado) {
        this.escaneoTerminado = escaneoTerminado;
    }

}
