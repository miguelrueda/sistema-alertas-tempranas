package jsf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.ListaProducto;

@ManagedBean
@SessionScoped
public class ListsService implements java.io.Serializable {
    
    private static final Logger LOG = Logger.getLogger(ListsService.class.getName());
    private List<ListaProducto> listas;

    public ListsService() {
    }
    
    public List<ListaProducto> crearListaDeListas() {
        listas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            listas.add(new ListaProducto((i + 1), ("Lista " + i), new Date()));
        }
        return listas;
    }
    
}