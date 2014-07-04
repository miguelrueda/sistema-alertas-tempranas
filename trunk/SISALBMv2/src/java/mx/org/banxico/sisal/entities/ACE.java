package mx.org.banxico.sisal.entities;

/**
 * Entidad que se encarga del acceso a la información del SW
 *
 * @author t41507
 * @version 04072014
 */
public class ACE implements java.io.Serializable {
    
    /**
     * Atributo de Serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos de la clase
     */
    private Integer idSoftware;
    private String SID;

    /**
     *  Constructor de la clase ACE
     */
    public ACE() {
    }

    /**
     * Constructor con parámetros
     *
     * @param idSoftware con un entero identificador
     * @param SID con una cadena de referencia
     */
    public ACE(Integer idSoftware, String SID) {
        this.idSoftware = idSoftware;
        this.SID = SID;
    }

    /**
     * Getter
     *
     * @return entero id de software
     */
    public int getIdSoftware() {
        return idSoftware;
    }

    /**
     * Setter
     *
     * @param idSoftware entero id de software
     */
    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    /**
     * Getter
     *
     * @return cadena con la referencia SID
     */
    public String getSID() {
        return SID;
    }

    /**
     * Setter
     *
     * @param SID cadena con la referencia SID
     */
    public void setSID(String SID) {
        this.SID = SID;
    }

    @Override
    public String toString() {
        return "ACE{" + "idSoftware=" + idSoftware + ", SID=" + SID + '}';
    }

}
