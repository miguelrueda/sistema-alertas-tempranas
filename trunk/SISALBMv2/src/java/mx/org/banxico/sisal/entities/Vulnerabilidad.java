package mx.org.banxico.sisal.entities;

import java.util.Date;

public class Vulnerabilidad implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private String idVulnerabilidad;
    private String severidad;
    private Date fechaPublicacion;
    private Date fechaModificacion;
    private String descripcion;
    private Double calificacionCVSS;
    private String vectorCVSS;

    public Vulnerabilidad() {
    }

    public Vulnerabilidad(String idVulnerabilidad, String severidad, Date fechaPublicacion, Date fechaModificacion, String descripcion, Double calificacionCVSS, String vectorCVSS) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.severidad = severidad;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaModificacion = fechaModificacion;
        this.descripcion = descripcion;
        this.calificacionCVSS = calificacionCVSS;
        this.vectorCVSS = vectorCVSS;
    }

    public String getVectorCVSS() {
        return vectorCVSS;
    }

    public void setVectorCVSS(String vectorCVSS) {
        this.vectorCVSS = vectorCVSS;
    }

    public String getIdVulnerabilidad() {
        return idVulnerabilidad;
    }

    public void setIdVulnerabilidad(String idVulnerabilidad) {
        this.idVulnerabilidad = idVulnerabilidad;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCalificacionCVSS() {
        return calificacionCVSS;
    }

    public void setCalificacionCVSS(Double calificacionCVSS) {
        this.calificacionCVSS = calificacionCVSS;
    }

    @Override
    public String toString() {
        return "Vulnerabilidad{" + "idVulnerabilidad=" + idVulnerabilidad + ", severidad=" + severidad + ", fechaPublicacion=" + fechaPublicacion + ", fechaModificacion=" + fechaModificacion + ", descripcion=" + descripcion + ", calificacionCVSS=" + calificacionCVSS + ", vectorCVSS=" + vectorCVSS + '}';
    }
}
