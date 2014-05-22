package mbs;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class LocalChangeListener implements ValueChangeListener {

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        UserData1 userData = (UserData1) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userData1");
        userData.setSelectedCountry(event.getNewValue().toString());
    }
}