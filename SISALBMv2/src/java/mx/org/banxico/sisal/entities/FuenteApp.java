package mx.org.banxico.sisal.entities;

import java.util.Date;

/**
 * Entidad que representa a la(s) fuente(s) de la aplicación
 *
 * @author t41507
 * @version 04072014
 */
public class FuenteApp implements java.io.Serializable {

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos principales de la clase
     */
    private Integer id;
    private String nombre;
    private String url;
    private Date fechaActualizacion;

    /**
     *  Constructor sin parámetros
     */
    public FuenteApp() {
    }

    /**
     * Constructor con parámetros
     *
     * @param id entero con el identificador de la fuente - necesario?
     * @param nombre cadena con el nombre de referencia
     * @param url cadena con la url de referencia de la fuente
     * @param fechaActualizacion fecha con la ultima actrualización
     */
    public FuenteApp(Integer id, String nombre, String url, Date fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Getter
     *
     * @return entero id de la fuente
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id entero id de la fuente
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre de la fuente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter
     *
     * @param nombre cadena con el nombre de la fuente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter
     *
     * @return cadena con la url de referencia
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter
     *
     * @param url cadena con la url de referencia
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter
     *
     * @return fecha de la ultima actualización
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Setter
     *
     * @param fechaActualizacion fecha de la ultima actualización
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "AppSource{" + "id=" + id + ", nombre=" + nombre + ", url=" + url + ", fechaActualizacion=" + fechaActualizacion + '}';
    }

}
