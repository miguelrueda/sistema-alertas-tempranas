package jsf.mgbns;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

//@ManagedBean(name = "assetsBean1")
@ManagedBean(name = "productsBean")
@SessionScoped
public class ProductsBean implements java.io.Serializable {

    private String acProducto;
    private String nombreLista;
    private List<Producto> productsList;
    private List<Producto> filteredProducts;
    private List<ListaProducto> prodListList;
    @ManagedProperty("#{productsService}")
    private ProductsService productsService;
    @ManagedProperty("#{listsService}")
    private ListsService listsService;
    private List<Producto> selectedProductsList;
    private static final Logger LOG = Logger.getLogger(ProductsBean.class.getName());
    private Producto selectedProduct;

    @PostConstruct
    public void init() {
        productsList = productsService.crearListaProductos(50);
        prodListList = listsService.crearListaDeListas();
    }

    public ProductsBean() {
    }

    public void showForm() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 400);
        RequestContext.getCurrentInstance().openDialog("/secured/configuration/createList", options, null);
    }

    public String getAcProducto() {
        return acProducto;
    }

    public void setAcProducto(String acProducto) {
        this.acProducto = acProducto;
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        return results;
    }

    public List<Producto> getProductsList() {
        if (productsList == null) {
            return new ArrayList<>();
        }
        return productsList;
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
    
    public List<Producto> getSelectedProductsList() {
        return selectedProductsList;
    }

    public void setSelectedProductsList(List<Producto> selectedProductsList) {
        this.selectedProductsList = selectedProductsList;
    }

    int i = 1;

    public void agregarProducto() {
        if (selectedProductsList == null) {
            selectedProductsList = new ArrayList<>();
        }
        selectedProductsList.add(new Producto(i++, "[Vendor] ", this.acProducto, " [version]"));
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    private int id = 100;
    private ListaProducto nuevaLista;

    public void save() {
        LOG.log(Level.INFO, "Tengo como nombre: {0} y tengo: {1} art\u00edculos en mi lista", new Object[]{nombreLista, selectedProductsList.size()});
        LOG.log(Level.INFO, "Creando lista");
        nuevaLista = new ListaProducto(id++, nombreLista, new Date(), selectedProductsList);
        LOG.log(Level.INFO, "Reseteando valores");
        acProducto = null;
        nombreLista = null;
        selectedProductsList = new ArrayList<>();
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        LOG.log(Level.INFO, "Cerrando Dialogo");
        RequestContext.getCurrentInstance().closeDialog("/secured/configuration/createList");
    }

    public void listCreated() {
        FacesMessage msg = new FacesMessage("Exito", "La lista fue creada exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        prodListList.add(nuevaLista);
    }

    public List<ListaProducto> getProdListList() {
        return prodListList;
    }

    public void setProdListList(List<ListaProducto> prodListList) {
        this.prodListList = prodListList;
    }

    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    public void setListsService(ListsService listsService) {
        this.listsService = listsService;
    }

    public Producto getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Producto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Producto> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Producto> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

}
