package org.banxico.ds.sisal.parser.entidades;

import java.util.List;

/**
 * Clase que representa la entidad software vulnerable
 *
 * @author t41507
 * @version 20.05.2014
 */
public class VulnSoftware implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * Atributos
     */
    private String vendor;
    private String name;
    private List<Version> version;

    /**
     * Constructor
     */
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
        vendor = vendor.replace("_", " ");
        char [] chars = vendor.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
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
        name = name.replace("_", " ");
        char [] chars = name.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
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
        //return "\nVulnerable Software {" + "vendor=" + vendor + ", name=" + name + ", version(s)=" + version + '}';
        vendor = vendor.replace("_", " ");
        char [] caracteres = vendor.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < caracteres.length; i++) {
            if (!found && Character.isLetter(caracteres[i])) {
                caracteres[i] = Character.toUpperCase(caracteres[i]);
                found = true;
            } else if (Character.isWhitespace(caracteres[i])) {
                found = false;
            }
        }
        vendor = String.valueOf(caracteres);
        name = name.replace("_", " ");
        found = false;
        char [] namechar = name.toLowerCase().toCharArray();
        for (int i = 0; i < namechar.length; i++) {
            if (!found && Character.isLetter(namechar[i])) {
                namechar[i] = Character.toUpperCase(namechar[i]);
                found = true;
            } else if (Character.isWhitespace(namechar[i])) {
                found = false;
            }
        }
        return vendor + "/" + name;
    }

}