package mx.org.banxico.sisal.scanner;

import java.util.logging.Logger;
import mx.org.banxico.sisal.entities.Software;
import mx.org.banxico.sisal.parser.entidades.CVE;

/**
 * Clase que representa un envoltorio para los resultados
 *
 * @author t41507
 * @version 04072014
 */
public class Result implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(Result.class.getName());

    /**
     * Atributos principales
     */
    private CVE vulnerabilidad;
    private Software sw;

    /**
     * Constructor
     */
    public Result() {
    }

    /**
     * Constructor con parámetros
     *
     * @param vulnerabilidad referencia de la vulnerabilidad
     * @param sw software vulnerable
     */
    public Result(CVE vulnerabilidad, Software sw) {
        this.vulnerabilidad = vulnerabilidad;
        this.sw = sw;
    }

    /**
     * Getter
     *
     * @return referencia de la vulenrabilidad
     */
    public CVE getVulnerabilidad() {
        return vulnerabilidad;
    }

    /**
     * Setter
     *
     * @param vulnerabilidad referencia de la vulenrabilidad
     */
    public void setVulnerabilidad(CVE vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    /**
     * Getter
     *
     * @return referencia del SW vulnerable
     */
    public Software getSw() {
        return sw;
    }

    /**
     * Setter
     *
     * @param sw referencia del SW vulnerable
     */
    public void setSw(Software sw) {
        this.sw = sw;
    }

    @Override
    public String toString() {
        return "La vulnerabilidad: " + vulnerabilidad.getName() + " puede afectar al sw: " + sw.getNombre() + "\nDetalle: " + vulnerabilidad.getDescription();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.sw != null ? this.sw.hashCode() : 0);
        hash = 53 * hash + (this.vulnerabilidad != null ? this.vulnerabilidad.hashCode() : 0);
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
        final Result other = (Result) obj;
        if (this.sw != other.sw && (this.sw == null || !this.sw.equals(other.sw))) {
            return false;
        }
        if (this.vulnerabilidad != other.vulnerabilidad && (this.vulnerabilidad == null || !this.vulnerabilidad.equals(other.vulnerabilidad))) {
            return false;
        }
        return true;
    }

}
