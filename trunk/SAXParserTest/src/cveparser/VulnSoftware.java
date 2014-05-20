package cveparser;

import java.util.List;

public class VulnSoftware {

    private String vendor;
    private String name;
    private List<Version> version;

    public VulnSoftware(String vendor, String name) {
        this.vendor = vendor;
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Version> getVersion() {
        return version;
    }

    public void setVersion(List<Version> version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "\nVulnerable Software {" + "vendor=" + vendor + ", name=" + name + ", \n\tversion(s)=" + version + '}';
    }

}
