package cveparser;

/**
 *  Clase que hace referencia al número de versión del SW, especificamente
 * el número de la versión y/o la edición del mismo
 * @author t41507
 */
public class Version {

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
        return "\n\t\tVersion{" + "number=" + number + ", edition=" + edition + '}';
    }

}
