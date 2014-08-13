package org.banxico.ds.sisal.entities;

/**
 * Entidad que representa a los grupos de la aplicación
 *
 * @author t41507
 * @version 13.08.2014
 */
public class Grupo implements java.io.Serializable {

    /**
     * Atributo LOGGER
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos de la Entidad
     */
    private int idGrupo;
    private String nombre;
    private String categoria;

    /**
     * Constructor
     */
    public Grupo() {
    }

    /**
     * Constructor
     *
     * @param idGrupo identificador del grupo
     * @param nombre nombre del grupo
     * @param categoria categoria del grupo
     */
    public Grupo(int idGrupo, String nombre, String categoria) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    /**
     * Getter
     *
     * @return cadena con la categoria del grupo
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setter
     *
     * @param categoria cadena con la categoria del grupo
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Getter
     *
     * @return entero con el identificador del grupo
     */
    public int getIdGrupo() {
        return idGrupo;
    }

    /**
     * Setter 
     *
     * @param idGrupo entero con el identificador del grupo
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Getter
     *
     * @return cadena con el nombre del grupo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter
     *
     * @param nombre cadena con el nombre del grupo
     */
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
