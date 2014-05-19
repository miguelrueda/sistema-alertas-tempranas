package cveparser;

import java.util.List;

public class VulnSoftware {

    private String vendor;
    private String name;
    private List<Version> version;

    public VulnSoftware(String vendor, String name, List<Version> version) {
        this.vendor = vendor;
        this.name = name;
        this.version = version;
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
        return "VulnSoftware{" + "vendor=" + vendor + ", name=" + name + ", version=" + version + '}';
    }

}
