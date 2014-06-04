package jpa.entities;

public class Producto implements java.io.Serializable {

    private Integer id;
    private String vendor;
    private String product;
    private String version;
    private Integer type;
    private Integer endOfLife;

    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Producto(Integer id, String vendor, String product, String version) {
        this.id = id;
        this.vendor = vendor;
        this.product = product;
        this.version = version;
    }

    public Producto(Integer id, String vendor, String product, String version, Integer type) {
        this.id = id;
        this.vendor = vendor;
        this.product = product;
        this.version = version;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEndOfLife() {
        return endOfLife;
    }

    public void setEndOfLife(Integer endOfLife) {
        this.endOfLife = endOfLife;
    }

    @Override
    public String toString() {
        //return "Producto{" + "id=" + id + ", vendor=" + vendor + ", product=" + product + ", version=" + version + ", type=" + type + ", endOfLife=" + endOfLife + '}';
        return "" + product + " " + version;
    }

}
