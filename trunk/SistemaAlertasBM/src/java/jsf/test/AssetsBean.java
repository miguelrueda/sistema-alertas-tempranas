package jsf.test;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author t41507
 */
@ManagedBean
@SessionScoped
public class AssetsBean implements java.io.Serializable {

    private List<Asset> assets;
    private List<Asset> filteredAssets;
    @ManagedProperty("#{assetsService}")
    private AssetsService service;
    private Asset selectedAsset;
    
    @PostConstruct
    public void init() {
        assets = service.createList(500);
    }
    
    public AssetsBean() {
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setService(AssetsService service) {
        this.service = service;
    }

    public Asset getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(Asset selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public List<Asset> getFilteredAssets() {
        return filteredAssets;
    }

    public void setFilteredAssets(List<Asset> filteredAssets) {
        this.filteredAssets = filteredAssets;
    }
    
}