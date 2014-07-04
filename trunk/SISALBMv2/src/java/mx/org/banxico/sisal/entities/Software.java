package mx.org.banxico.sisal.entities;

/**
 * Entidad que representa al SW
 *
 * @author t41507
 * @version 04072014
 */
public class Software implements java.io.Serializable {

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos principales de la clase
    */
    private Integer idSoftware;
    private String fabricante;
    private String nombre;
    private String version;
    private Integer tipo;
    private Integer endoflife;
    private String UAResponsable;
    private String AnalistaResponsable;

    /**
     *  Constructor sin parámetros
     */
    public Software() {
    }

    /**
     * Constructor con parámetros
     *
     * @param idSoftware entero con el id del SW
     * @param fabricante cadena con el nombre del fabricante
     * @param nombre cadena con el nombre del SW
     * @param version cadena con la versión del SW
     * @param tipo entero con el tipo de SW 1 para SW 2 para OS - Verificar
     * @param endoflife entero con el fin del ciclo 1 para terminado, 2 para vivo
     */
    public Software(Integer idSoftware, String fabricante, String nombre, String version, Integer tipo, Integer endoflife) {
        this.idSoftware = idSoftware;
        this.fabricante = fabricante;
        this.nombre = nombre;
        this.version = version;
        this.tipo = tipo;
        this.endoflife = endoflife;
    }

    /**
     * Constructor con parámetros
     *
     * @param idSoftware entero con el id del SW
     * @param fabricante cadena con el nombre del fabricante
     * @param nombre cadena con el nombre del SW
     * @param version cadena con la versión del SW
     * @param tipo entero con el tipo de SW 1 para SW 2 para OS - Verificar
     * @param endoflife entero con el fin del ciclo 1 para terminado, 2 para vivo
     * @param UAResponsable cadena con la referencia de la UA Responsable
     * @param AnalistaResponsable cadena con el nombre del analista
     */
    public Software(Integer idSoftware, String fabricante, String nombre, String version, Integer tipo, Integer endoflife, String UAResponsable, String AnalistaResponsable) {
        this.idSoftware = idSoftware;
        this.fabricante = fabricante;
        this.nombre = nombre;
        this.version = version;
        this.tipo = tipo;
        this.endoflife = endoflife;
        this.UAResponsable = UAResponsable;
        this.AnalistaResponsable = AnalistaResponsable;
    }

    /**
     * Getter
     *
     * @return entero con el tipo de SW
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * Setter
     *
     * @param tipo entero con el tipo de SW
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * Getter
     *
     * @return cadena con el fabricante del sw
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Setter
     *
     * @param fabricante cadena con el fabricante del sw
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre del SW
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter
     *
     * @param nombre cadena con el nombre del SW
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter
     *
     * @return cadena con la version del SW
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter
     *
     * @param version cadena con la version del SW
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter
     *
     * @return entero con el Id del SW
     */
    public Integer getIdSoftware() {
        return idSoftware;
    }

    /**
     * Setter
     *
     * @param idSoftware entero con el Id del SW
     */
    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    /**
     * Getter
     *
     * @return entero con el fin de ciclo
     */
    public Integer getEndoflife() {
        return endoflife;
    }

    /**
     * Setter
     *
     * @param endoflife entero con el fin de ciclo
     */
    public void setEndoflife(Integer endoflife) {
        this.endoflife = endoflife;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre de la UA Responsable
     */
    public String getUAResponsable() {
        return UAResponsable;
    }

    /**
     * Setter
     *
     * @param UAResponsable cadena con el nombre de la UA Responsable
     */
    public void setUAResponsable(String UAResponsable) {
        this.UAResponsable = UAResponsable;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre del analista
     */
    public String getAnalistaResponsable() {
        return AnalistaResponsable;
    }

    /**
     * Setter
     *
     * @param AnalistaResponsable cadena con el nombre del analista
     */
    public void setAnalistaResponsable(String AnalistaResponsable) {
        this.AnalistaResponsable = AnalistaResponsable;
    }

    @Override
    public String toString() {
        return "Software{" + "idProducto=" + idSoftware + ", proveedor=" + fabricante + ", nombre=" + nombre + ", version=" + version + ", tipo=" + tipo + ", endoflife=" + endoflife + '}';
    }

}
