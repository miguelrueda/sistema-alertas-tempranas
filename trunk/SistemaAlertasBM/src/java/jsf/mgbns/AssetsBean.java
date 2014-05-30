package jsf.mgbns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "assetsBean1")
@SessionScoped
public class AssetsBean implements java.io.Serializable {

    private String acProducto;

    public AssetsBean() {
    }

    public void showForm() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        RequestContext.getCurrentInstance().openDialog("/secured/configuration/createList", options, null);
    }

    public String getAcProducto() {
        return acProducto;
    }

    public void setAcProducto(String acProducto) {
        this.acProducto = acProducto;
    }
    
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        return results;
    }

}