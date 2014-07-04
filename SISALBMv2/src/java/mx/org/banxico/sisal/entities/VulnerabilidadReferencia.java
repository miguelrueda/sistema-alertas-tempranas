package mx.org.banxico.sisal.entities;

/**
 * Entidad que representa la relación entre la vulnerabilidad y sus referencias
 *
 * @author t41507
 * @version 04072014
 */
public class VulnerabilidadReferencia implements java.io.Serializable {
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos principales
     */
    private String idVulnerabilidad;
    private Integer idReferencia;
    private String fuente;
    private String url;

    /**
     * Constructor
     */
    public VulnerabilidadReferencia() {
    }

    /**
     * Constructor con párametros
     *
     * @param idVulnerabilidad cadena con el nombre de la vulnerabilidad
     * @param idReferencia entero con el id de la referencia
     * @param fuente cadena con la fuente de la referencia
     * @param url cadena con la url de la referencia
     */
    public VulnerabilidadReferencia(String idVulnerabilidad, Integer idReferencia, String fuente, String url) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.idReferencia = idReferencia;
        this.fuente = fuente;
        this.url = url;
    }

    /**
     * Getter
     *
     * @return cadena con la url de la referencia
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter
     *
     * @param url cadena con la url de la referencia
     */
    public void setUrl(String url) {
        this.url = url;
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

    /**
     * Getter
     *
     * @return entero con el id de la referencia
     */
    public Integer getIdReferencia() {
        return idReferencia;
    }

    /**
     * Setter
     *
     * @param idReferencia entero con el id de la referencia
     */
    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    /**
     * Getter
     *
     * @return cadena con la fuente de la referencia
     */
    public String getFuente() {
        return fuente;
    }

    /**
     * Setter
     *
     * @param fuente cadena con la fuente de la referencia
     */
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    @Override
    public String toString() {
        return "VulnerabilidadReferencia{" + "idVulnerabilidad=" + idVulnerabilidad + ", idReferencia=" + idReferencia + ", fuente=" + fuente + ", url=" + url + '}';
    }

}
