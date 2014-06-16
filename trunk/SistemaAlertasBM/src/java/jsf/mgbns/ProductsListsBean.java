package jsf.mgbns;

import ejb.model.SoftwareEJB;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.ListaProducto;
import jpa.entities.Producto;
import jsf.services.ListsService;
import jsf.services.ProductsService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

/**
 * Bean para obtener y crear las listas de productos
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean
@SessionScoped
public class ProductsListsBean implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ProductsListsBean.class.getName());
    /**
     * Atributos de el Bean
     */
    private List<Producto> productsList;
    private List<ListaProducto> prodListList;
    private ListaProducto nuevaLista;
    private String acProducto;
    private String nombreLista;
    private List<Producto> selectedProductsList;
    private int idLista = 100;
    int i = 1;
    @EJB
    private SoftwareEJB productosService;
    /**
     * Inyección de servicio de listas
     */
    @ManagedProperty("#{listsService}")
    private ListsService listsService;
    //@ManagedProperty("#{productsService}")
    //private ProductsService productsService;

    @PostConstruct
    public void init() {
        //productsList = productsService.crearListaProductos(50);
        productsList = productosService.getListaProductos();
        prodListList = listsService.crearListaDeListas();
    }

    public ProductsListsBean() {
    }

    public List<ListaProducto> getProdListList() {
        return prodListList;
    }

    public void setProdListList(List<ListaProducto> prodListList) {
        this.prodListList = prodListList;
    }

    public void setListsService(ListsService listsService) {
        this.listsService = listsService;
    }

    /*
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }*/

    public void showForm() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 400);
        options.put("contentWidth", 800);
        RequestContext.getCurrentInstance().openDialog("/secured/configuration/createList", options, null);
    }

    public void listCreated() {
        FacesMessage msg = new FacesMessage("Exito", "La lista fue creada exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        prodListList.add(nuevaLista);
    }

    public void save() {
        LOG.log(Level.INFO, "Tengo como nombre: {0} y tengo: {1} art\u00edculos en mi lista", new Object[]{nombreLista, selectedProductsList.size()});
        LOG.log(Level.INFO, "Creando lista");
        nuevaLista = new ListaProducto(idLista++, nombreLista, new Date(), selectedProductsList);
        LOG.log(Level.INFO, "Reseteando valores");
        acProducto = null;
        nombreLista = null;
        selectedProductsList = new ArrayList<>();
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        LOG.log(Level.INFO, "Cerrando Dialogo");
        RequestContext.getCurrentInstance().closeDialog("/secured/configuration/createList");
    }

    public String getAcProducto() {
        return acProducto;
    }

    public void setAcProducto(String acProducto) {
        this.acProducto = acProducto;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public List<Producto> getSelectedProductsList() {
        return selectedProductsList;
    }

    public void setSelectedProductsList(List<Producto> selectedProductsList) {
        this.selectedProductsList = selectedProductsList;
    }

    public void agregarProducto() {
        if (selectedProductsList == null) {
            selectedProductsList = new ArrayList<>();
        }
        //selectedProductsList.add(new Producto(i++, "[Vendor] ", this.acProducto, " [version]"));
        LOG.log(Level.INFO, "Buscando el producto: {0}", this.acProducto);
        selectedProductsList.add(productosService.getProductoPorNombre(this.acProducto.trim()));
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        return results;
    }

    public List<Producto> completarProducto(String qry) {
        List<Producto> filtered = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            Producto prd = productsList.get(i);
            if (prd.getProduct().toLowerCase().contains(qry.toLowerCase())) {
                filtered.add(prd);
            }
        }
        return filtered;
    }
}


/*

 
    
    
  
 


    

 */
