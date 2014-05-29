package softwareparser;

public class Software {

    private String vendor;
    private String product;
    private String version;
    private int type;
    private int endoflife;

    public Software() {
    }

    public Software(String msVendor, String msProduct, String msVersion, int mnType, int mnEndOfLife) {
        this.vendor = msVendor;
        this.product = msProduct;
        this.version = msVersion;
        this.type = mnType;
        this.endoflife = mnEndOfLife;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEndoflife() {
        return endoflife;
    }

    public void setEndoflife(int endoflife) {
        this.endoflife = endoflife;
    }

    @Override
    public String toString() {
        return "\nSoftware{" + "vendor=" + vendor + ", product=" + product + ", version=" + version + ", type=" + type + ", endoflife=" + endoflife + '}';
    }

}
