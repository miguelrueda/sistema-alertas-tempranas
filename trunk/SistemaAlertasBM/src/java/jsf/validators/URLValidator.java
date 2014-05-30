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

@FacesValidator("urlValidator")
public class URLValidator implements javax.faces.validator.Validator {

    private static final Logger LOG = Logger.getLogger(URLValidator.class.getName());

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

    private boolean validateUrl(String sValue) {
        Pattern urlPattern = Pattern.compile("((https?|ftp|file):((//)|(\\\\\\\\))+[\\\\w\\\\d:#@%/;$()~_?\\\\+-=\\\\\\\\\\\\.&]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(sValue);
        return matcher.find();
    }

}
