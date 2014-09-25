package org.banxico.ds.sisal.entities;

import java.util.Date;
import java.util.List;

/**
 * Entidad que representa a una vulnerabilidad
 *
 * @author t41507
 * @version 04072014
 */
public class Vulnerabilidad implements java.io.Serializable, java.lang.Comparable {

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos principales
     */
    private String idVulnerabilidad;
    private String severidad;
    private Date fechaPublicacion;
    private Date fechaModificacion;
    private String descripcion;
    private Double calificacionCVSS;
    private String vectorCVSS;
    private List<Software> listaSoftware;

    /**
     * Constructor
     */
    public Vulnerabilidad() {
    }

    /**
     * Constructor con parámetros
     *
     * @param idVulnerabilidad nombre de la vulnerabilidad
     */
    public Vulnerabilidad(String idVulnerabilidad) {
        this.idVulnerabilidad = idVulnerabilidad;
    }

    /**
     * Constructor con parámetros
     *
     * @param idVulnerabilidad cadena con el nombre de la vulnerabilidad
     * @param severidad cadena con el tipo de criticidad
     * @param fechaPublicacion fecha de publicación
     * @param fechaModificacion fecha de modificación
     * @param descripcion cadena que tiene la descripción de la vulnerabilidad
     * @param calificacionCVSS valor con la calificación de la vulnerabilidad
     * @param vectorCVSS cadena con el vector
     */
    public Vulnerabilidad(String idVulnerabilidad, String severidad, Date fechaPublicacion, Date fechaModificacion, String descripcion, Double calificacionCVSS, String vectorCVSS) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.severidad = severidad;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaModificacion = fechaModificacion;
        this.descripcion = descripcion;
        this.calificacionCVSS = calificacionCVSS;
        this.vectorCVSS = vectorCVSS;
    }

    /**
     * Getter
     *
     * @return cadena con el vector
     */
    public String getVectorCVSS() {
        return vectorCVSS;
    }

    /**
     * Setter
     *
     * @param vectorCVSS cadena con el vector
     */
    public void setVectorCVSS(String vectorCVSS) {
        this.vectorCVSS = vectorCVSS;
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
     * @return cadena con la criticidad de la vulnerabilidad
     */
    public String getSeveridad() {
        return severidad;
    }

    /**
     * Setter
     *
     * @param severidad cadena con la criticidad de la vulnerabilidad
     */
    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    /**
     * Getter
     *
     * @return fecha de publicación
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Setter
     *
     * @param fechaPublicacion fecha de publicación
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Getter
     *
     * @return fecha de modificación
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Setter
     *
     * @param fechaModificacion fecha de modificación
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * Getter
     *
     * @return cadena con la descripción de la vulnerabilidad
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter
     *
     * @param descripcion cadena con la descripción de la vulnerabilidad
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter
     *
     * @return valor doble con la calificación de la vulnerabilidad
     */
    public Double getCalificacionCVSS() {
        return calificacionCVSS;
    }

    /**
     * Setter
     *
     * @param calificacionCVSS valor doble con la calificación de la
     * vulnerabilidad
     */
    public void setCalificacionCVSS(Double calificacionCVSS) {
        this.calificacionCVSS = calificacionCVSS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.idVulnerabilidad != null ? this.idVulnerabilidad.hashCode() : 0);
        hash = 47 * hash + (this.severidad != null ? this.severidad.hashCode() : 0);
        hash = 47 * hash + (this.fechaPublicacion != null ? this.fechaPublicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vulnerabilidad other = (Vulnerabilidad) obj;
        if ((this.idVulnerabilidad == null) ? (other.idVulnerabilidad != null) : !this.idVulnerabilidad.equals(other.idVulnerabilidad)) {
            return false;
        }
        if ((this.severidad == null) ? (other.severidad != null) : !this.severidad.equals(other.severidad)) {
            return false;
        }
        if (this.fechaPublicacion != other.fechaPublicacion && (this.fechaPublicacion == null || !this.fechaPublicacion.equals(other.fechaPublicacion))) {
            return false;
        }
        return true;
    }

    /**
     * Método que se encarga de retornar la lista de software vulnerable
     *
     * @return Lista de software vulnerable
     */
    public List<Software> getListaSoftware() {
        return listaSoftware;
    }

    /**
     * SETTER
     *
     * @param listaSoftware
     */
    public void setListaSoftware(List<Software> listaSoftware) {
        this.listaSoftware = listaSoftware;
    }

    @Override
    public String toString() {
        return "Vulnerabilidad{" + "idVulnerabilidad=" + idVulnerabilidad + ", severidad=" + severidad + ", fechaPublicacion=" + fechaPublicacion + ", fechaModificacion=" + fechaModificacion + ", descripcion=" + descripcion + ", calificacionCVSS=" + calificacionCVSS + ", vectorCVSS=" + vectorCVSS + '}';
    }

    @Override
    public int compareTo(Object o) {
        Date thisdate = this.fechaPublicacion;
        Date otra = ((Vulnerabilidad) o).getFechaPublicacion();
        if (thisdate == null || otra == null) {
            return 0;
        }
        return thisdate.compareTo(otra);
    }
}