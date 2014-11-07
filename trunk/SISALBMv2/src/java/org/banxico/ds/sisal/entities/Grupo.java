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
    private int reporta;
    private String correo;

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
     * Constructor
     *
     * @param idGrupo identificador del grupo
     * @param nombre nombre del grupo
     * @param categoria categoria del grupo
     * @param reporta indica si el grupo se reporta para seguimiento
     * @param correo correo electronico del grupo al cual pertenece
     */
    public Grupo(int idGrupo, String nombre, String categoria, int reporta, String correo) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.reporta = reporta;
        this.correo = correo;
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

    /**
     * Getter
     * 
     * @return  indica si el grupo se reporta o no
     */
    public int getReporta() {
        return reporta;
    }

    /**
     * Setter
     * 
     * @param reporta entero con el valor de la bandera
     */
    public void setReporta(int reporta) {
        this.reporta = reporta;
    }

    /**
     * Getter
     * 
     * @return Correo electronico de la oficina a la que pertenece el grupo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Setter
     * 
     * @param correo cadena con el correo electronico de la oficina a la cual pertenece el grupo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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
        return "Grupo{id=" + idGrupo + ", nombre=" + nombre + ", categoria=" + categoria + ", reporta=" + reporta + ", correo=" + correo + "}";
    }

}
