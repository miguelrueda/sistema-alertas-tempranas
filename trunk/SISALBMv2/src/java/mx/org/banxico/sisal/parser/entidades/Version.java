package mx.org.banxico.sisal.parser.entidades;

/**
 *  Clase que hace referencia al número de versión del SW, especificamente
 * el número de la versión y/o la edición del mismo
 * @author t41507
 */
public class Version implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * Atributos
     */
    private String number;
    private String edition;

    /**
     *Constructor que recibé el parámetro: número de versión
     * @param number número de la versión
     */
    public Version(String number) {
        this.number = number;
    }

    /**
     *GETTER
     * @return número de versión
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter
     * @param number número de la versión
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Getter
     * @return edición del SW
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Setter
     * @param edition edición del SW
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //return "{" + number + ", edition=" + edition + '}';
        sb.append("{");
        if (!number.equals("") && !number.equals("-")) {
            sb.append(number);
        } else {
            sb.append("ND");
        }
        if (!edition.equals("-1") && !edition.equals("") && !edition.equals("-")) {
            sb.append(", ").append("edición: ").append(edition); 
        }
        sb.append("}");
        return sb.toString();
    }

}