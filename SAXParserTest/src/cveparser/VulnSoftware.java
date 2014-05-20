package cveparser;

import java.util.List;

/**
 * Clase que representa la entidad software vulnerable
 *
 * @author t41507
 * @version 20.05.2014
 */
public class VulnSoftware {

    /**
     * Atributos
     */
    private String vendor;
    private String name;
    private List<Version> version;

    public VulnSoftware() {
    }

    /**
     * Constructor
     *
     * @param vendor fabricante del software
     * @param name nombre del producto
     */
    public VulnSoftware(String vendor, String name) {
        this.vendor = vendor;
        this.name = name;
    }

    /**
     * GETTER
     *
     * @return fabricante del software
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * SETTER
     *
     * @param vendor fabricante del software
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * GETTER
     *
     * @return nombre del producto
     */
    public String getName() {
        return name;
    }

    /**
     * SETTER
     *
     * @param name nombre del producto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * GETTER
     *
     * @return lista adjunta de versiones vulnerables
     */
    public List<Version> getVersion() {
        return version;
    }

    /**
     * SETTER
     *
     * @param version lista adjunta de versiones vulnerables
     */
    public void setVersion(List<Version> version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "\nVulnerable Software {" + "vendor=" + vendor + ", name=" + name + ", \n\tversion(s)=" + version + '}';
    }

}
