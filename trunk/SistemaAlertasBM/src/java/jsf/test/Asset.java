package jsf.test;

/**
 *
 * @author t41507
 * @version 28.05.2014
 */
public class Asset implements java.io.Serializable {

    private int id;
    private String vendor;
    private String software;
    private String version;

    public Asset() {
    }

    public Asset(int id, String vendor, String software, String version) {
        this.id = id;
        this.vendor = vendor;
        this.software = software;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

}
