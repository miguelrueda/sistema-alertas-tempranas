package jsf.mgbns;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import jpa.entities.Producto;
import jsf.services.ProductsService;

/**
 * Bean que se encarga de obtener y representar las listas de productos hacia
 * las vistas
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean(name = "productsBean")     //@ManagedBean(name = "assetsBean1")
@SessionScoped
public class ProductsBean implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ProductsBean.class.getName());
    /**
     * Atributos principales del Bean
     */
    private List<Producto> productsList;
    private List<Producto> filteredProducts;
    private Producto selectedProduct;

    /**
     * Inyección de servicios para el Bean
     */
    @ManagedProperty("#{productsService}")
    private ProductsService productsService;

    /**
     * Mét odo de inicialización para el bean
     */
    @PostConstruct
    public void init() {
        productsList = productsService.crearListaProductos(50);
        //prodListList = listsService.crearListaDeListas();
    }

    /**
     * Constructor sin parámetros
     */
    public ProductsBean() {
    }

    /**
     * Mét odo para obtener la lista de productos del servicio
     *
     * @return List con los productos disponibles
     */
    public List<Producto> getProductsList() {
        /*
         if (productsList == null) {
         return new ArrayList<>();
         }*/
        if (productsList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return productsList;
    }

    /**
     * Mét odo setter para el servicio de productos
     *
     * @param productsService recibe el servicio de tipo ProductsService
     */
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    /**
     * Mét odo getter para devolver el producto seleccioando
     *
     * @return Producto seleccionado
     */
    public Producto getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Mét odo setter para el producto seleccionado
     *
     * @param selectedProduct Producto seleccionado
     */
    public void setSelectedProduct(Producto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    /**
     * Mét odo getter para obtener la lista de productos filtrada
     *
     * @return List con los productos filtrados
     */
    public List<Producto> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * Mét odo setter para la lista de productos filtrados
     *
     * @param filteredProducts List con los productos filtrados.
     */
    public void setFilteredProducts(List<Producto> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

}
