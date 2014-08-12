package org.banxico.ds.sisal.entities;

public class Grupo implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private int idGrupo;
    private String nombre;
    private String categoria;

    public Grupo() {
    }

    public Grupo(int idGrupo, String nombre, String categoria) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.idGrupo;
        hash = 11 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Grupo other = (Grupo) obj;
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grupo{" + "idGrupo=" + idGrupo + ", nombre=" + nombre + ", categoria=" + categoria + '}';
    }

}
