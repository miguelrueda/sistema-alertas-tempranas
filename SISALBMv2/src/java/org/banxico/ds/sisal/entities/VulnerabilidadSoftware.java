package org.banxico.ds.sisal.entities;

/**
 * Entidad que representa la relación entre la vulnerabilidad y el SW Vulnerable
 *
 * @author t41507
 * @version 04072014
 */
public class VulnerabilidadSoftware implements java.io.Serializable {

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos principales
     */
    private String idVulnerabilidad;
    private Integer idSoftware;

    /**
     * Constructor
     */
    public VulnerabilidadSoftware() {
    }

    /**
     * Constructor con parámetros
     *
     * @param idVulnerabilidad cadena con el nombre de la vulnerabilidad
     * @param idSoftware entero con el id del software
     */
    public VulnerabilidadSoftware(String idVulnerabilidad, Integer idSoftware) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.idSoftware = idSoftware;
    }

    /**
     * Getter
     *
     * @return entero con el id del SW
     */
    public Integer getIdSoftware() {
        return idSoftware;
    }

    /**
     * Setter
     *
     * @param idSoftware entero con el id del SW
     */
    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre de la vulnerabilidad
     */
    public String getIdVulnerabilidad() {
        return idVulnerabilidad;
    }

    /**
     * Setter
     *
     * @param idVulnerabilidad cadena con el nombre de la vulnerabilidad
     */
    public void setIdVulnerabilidad(String idVulnerabilidad) {
        this.idVulnerabilidad = idVulnerabilidad;
    }

    @Override
    public String toString() {
        return "VulnerabilidadSoftware{" + "idVulnerabilidad=" + idVulnerabilidad + ", idSoftware=" + idSoftware + '}';
    }

}
