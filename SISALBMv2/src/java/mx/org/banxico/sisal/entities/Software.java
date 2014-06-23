package mx.org.banxico.sisal.entities;

public class Software implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private int idProducto;
    private String proveedor;
    private String nombre;
    private String version;
    private int tipo;
    private int endoflife;

    public Software() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEndoflife() {
        return endoflife;
    }

    public void setEndoflife(int endoflife) {
        this.endoflife = endoflife;
    }

    @Override
    public String toString() {
        return "Software{" + "idProducto=" + idProducto + ", proveedor=" + proveedor + ", nombre=" + nombre + ", version=" + version + ", tipo=" + tipo + ", endoflife=" + endoflife + '}';
    }

}
