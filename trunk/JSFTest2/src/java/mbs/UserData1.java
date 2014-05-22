package mbs;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "userData1", eager = true)
@SessionScoped
public class UserData1 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private static Map<String, String> countryMap;
    private String selectedCountry = "United Kingdom";

    static {
        countryMap = new LinkedHashMap<>();
        countryMap.put("en", "United Kingdom");
        countryMap.put("fr", "French");
        countryMap.put("de", "German");
    }

    public UserData1() {
    }

    public void localeChanged(ValueChangeEvent e) {
        selectedCountry = e.getNewValue().toString();
    }

    public Map<String, String> getCountryMap() {
        return countryMap;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

}
