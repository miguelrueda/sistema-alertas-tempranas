package jsf.converters;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.ListaProducto;

/**
 *
 * @author t41507
 */
@FacesConverter(value = "productListConverter")
public class ProductListConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(ProductListConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LOG.log(Level.INFO, "ASOB - Llega: {0}", value);
        if (value.trim().equals("")) {
            return "TEMP"; 
        } else {
            //return new ListaProducto(Integer.parseInt(value));
            return new ListaProducto(1, value);
            //return "TEMP";
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.INFO, "ASST - LLega: {0}", value.toString());
        if (value == null || value.equals("")) {
            return "TEMP";
        } else {
            LOG.log(Level.INFO, "RECIBO --> {0}", value);
            LOG.log(Level.INFO, "String creado");
            //return String.valueOf(new ListaProducto(Integer.parseInt(value.toString())).getNombre());
            //return ((ListaProducto) vale).getNombre();
            ListaProducto nueva = new ListaProducto(Integer.parseInt(value.toString()), "Lista " + value);
            return nueva.getNombre();
        }
    }

}