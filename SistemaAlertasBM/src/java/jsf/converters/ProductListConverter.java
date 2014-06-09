package jsf.converters;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author t41507
 */
public class ProductListConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(ProductListConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LOG.log(Level.INFO, "FacesContext: {0}", context);
        LOG.log(Level.INFO, "UIComponent: {0}", component);
        LOG.log(Level.INFO, "VALUE: {0}", value);
        return "VALUERETURNED";
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.INFO, "FacesContext: {0}", context);
        LOG.log(Level.INFO, "UIComponent: {0}", component);
        LOG.log(Level.INFO, "VALUE: {0}", value);
        return "VALUERETURNED";
    }

}
