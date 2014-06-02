package jsf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.Producto;

@ManagedBean
@SessionScoped
public class ProductsService implements java.io.Serializable {

    private List<Producto> productsList;
    private static final Logger LOG = Logger.getLogger(ProductsService.class.getName());
    private final static String [] vendors;
    private final static String [] products;
    private final static String [] versions;
    
    static {
        vendors = new String[5];
        vendors[0] = "Microsoft";
        vendors[1] = "Apache";
        vendors[2] = "Oracle";
        vendors[3] = "Adobe";
        vendors[4] = "Canonical";
        products = new String[5];
        products[0] = "Windows 7";
        products[1] = "Adobe Reader";
        products[2] = "MySQL";
        products[3] = "Tomcat";
        products[4] = "Ubuntu";
        versions = new String[5];
        versions[0] = "1.0";
        versions[1] = "4.0.1";
        versions[2] = "5.0";
        versions[3] = "1.3";
        versions[4] = "1.1.3";
    }

    public ProductsService() {
    }

    public List<Producto> crearListaProductos(int size) {
        productsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            productsList.add(new Producto((i + 1), getRandomVendor(), getRandomProduct(), getRandomVersion()));
        }
        return productsList;
    }
    
    public String getRandomVendor() {
        return vendors[(int) (Math.random() * 5)];
    }
    
    public String getRandomProduct() {
        return products[(int) (Math.random() * 5)];
    }
    
    public String getRandomVersion() {
        return versions[(int) (Math.random() * 5)];
    }

}