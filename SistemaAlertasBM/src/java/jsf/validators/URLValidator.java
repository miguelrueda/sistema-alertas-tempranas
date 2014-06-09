package jsf.validators;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * Validador para la URL de las fuentes ingresadas
 *
 * @author t41507
 * @version 09.06.2014
 */
@FacesValidator("urlValidator")
public class URLValidator implements javax.faces.validator.Validator {

    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(URLValidator.class.getName());

    /**
     * Mét odo validar sobrecargado para verificar la URL
     *
     * @param context de tipo FacesContext
     * @param component de tipo UIComponent
     * @param value de tipo Object, es el valor a validar
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String sValue = (String) value;
        boolean flag = false;
        flag = validateUrl(sValue);
        if (!flag) {
            FacesMessage msg = new FacesMessage("La URL no es válida");
            msg.setDetail("La URL debe cumplir con el prefijo: ([http|https]://).");
            //La URL NO ES VALIDA
            throw new ValidatorException(msg);
        }
    }

    /**
     * Mét odo para comprobar la URL mediante una expresion regular
     * 
     * @param sValue valor de la URL a validar
     * @return  boolean con el resultado del match
     */
    private boolean validateUrl(String sValue) {
        Pattern urlPattern = Pattern.compile("((https?|ftp|file):((//)|(\\\\\\\\))+[\\\\w\\\\d:#@%/;$()~_?\\\\+-=\\\\\\\\\\\\.&]*)", 
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(sValue);
        return matcher.find();
    }

}
