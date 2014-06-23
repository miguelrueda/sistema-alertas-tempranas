package mx.org.banxico.sisal.entities;

import java.util.Date;

public class AppSource implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String url;
    private Date fechaActualizacion;

    public AppSource() {
    }

    public AppSource(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
