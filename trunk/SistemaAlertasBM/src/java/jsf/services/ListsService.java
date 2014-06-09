package jsf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.ListaProducto;

/**
 * Servicio para obtener las listas con las referencias de los artículos que
 * tienen
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean
@SessionScoped
public class ListsService implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ListsService.class.getName());
    /**
     * Atributos del servicio
     */
    private List<ListaProducto> listas;

    /**
     * Constructor sin parámetros
     */
    public ListsService() {
    }

    /**
     * Mét odo para crear las listas de productos del sistema
     *
     * @return List con las listas de productos
     */
    public List<ListaProducto> crearListaDeListas() {
        listas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            listas.add(new ListaProducto((i + 1), ("Lista " + i), new Date()));
        }
        return listas;
    }

}
