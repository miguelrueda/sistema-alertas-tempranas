package entidad;

import java.io.Serializable;

public class Piloto implements Serializable {

    private String nombre;
    private int puntos;

    public Piloto() {
    }

    public Piloto(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public Piloto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Piloto{" + "nombre=" + nombre + ", puntos=" + puntos + '}';
    }
    
    public String actualizarNombre(String nombre) {
        this.nombre = nombre;
        return nombre;
    }
    
    public int actualizarPuntos(int puntos) {
        this.puntos = puntos;
        return puntos;
    }
}
