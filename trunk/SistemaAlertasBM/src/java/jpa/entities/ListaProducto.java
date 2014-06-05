package jpa.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ListaProducto implements java.io.Serializable {

    private Integer id;
    private String nombre;
    private Date fechaCreacion;
    private List<Producto> productos;

    public ListaProducto(Integer id) {
        this.id = id;
    }

    public ListaProducto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ListaProducto(Integer id, String nombre, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.productos = generarListaAleatoria();
    }

    public ListaProducto(Integer id, String nombre, Date fechaCreacion, List<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.productos = productos;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    private List<Producto> generarListaAleatoria() {
        Random rd = new Random();
        int temp = rd.nextInt(5);
        List<Producto> tempList = new ArrayList<>();
        for (int i = 0; i < temp; i++) {
            tempList.add(new Producto((i + 1), "Fabricante " + (i + 1), "Producto " + (i + 1), "Version " + (i + 1)));
        }
        return tempList;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
