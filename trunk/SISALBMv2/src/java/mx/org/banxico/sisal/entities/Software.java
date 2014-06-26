package mx.org.banxico.sisal.entities;

public class Software implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private Integer idSoftware;
    private String fabricante;
    private String nombre;
    private String version;
    private Integer tipo;
    private Integer endoflife;
    private String UAResponsable;
    private String AnalistaResponsable;

    public Software() {
    }

    public Software(Integer idSoftware, String fabricante, String nombre, String version, Integer tipo, Integer endoflife) {
        this.idSoftware = idSoftware;
        this.fabricante = fabricante;
        this.nombre = nombre;
        this.version = version;
        this.tipo = tipo;
        this.endoflife = endoflife;
    }

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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    public Integer getEndoflife() {
        return endoflife;
    }

    public void setEndoflife(Integer endoflife) {
        this.endoflife = endoflife;
    }

    public String getUAResponsable() {
        return UAResponsable;
    }

    public void setUAResponsable(String UAResponsable) {
        this.UAResponsable = UAResponsable;
    }

    public String getAnalistaResponsable() {
        return AnalistaResponsable;
    }

    public void setAnalistaResponsable(String AnalistaResponsable) {
        this.AnalistaResponsable = AnalistaResponsable;
    }

    @Override
    public String toString() {
        return "Software{" + "idProducto=" + idSoftware + ", proveedor=" + fabricante + ", nombre=" + nombre + ", version=" + version + ", tipo=" + tipo + ", endoflife=" + endoflife + '}';
    }

}
