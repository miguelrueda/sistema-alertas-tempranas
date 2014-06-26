package mx.org.banxico.sisal.entities;

import java.util.Date;

public class FuenteApp implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    private String nombre;
    private String url;
    private Date fechaActualizacion;

    public FuenteApp() {
    }

    public FuenteApp(Integer id, String nombre, String url, Date fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "AppSource{" + "id=" + id + ", nombre=" + nombre + ", url=" + url + ", fechaActualizacion=" + fechaActualizacion + '}';
    }

}
