package mx.org.banxico.sisal.scanner;

import java.util.ArrayList;
import java.util.List;
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
    private List<Software> swList;
    private String grupo;
    private List<String> gruposList;

    /**
     * Constructor
     */
    public Result() {
        swList = new ArrayList<Software>();
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
     * Constructor con parámetros
     *
     * @param vulnerabilidad referencia de la vulnerabilidad
     * @param swList lista de software vulnerable
     */
    public Result(CVE vulnerabilidad, List<Software> swList) {
        this.vulnerabilidad = vulnerabilidad;
        this.swList = swList;
    }

    /**
     * Constructor con parametros
     *
     * @param vulnerabilidad referencia a la vulnerabilidad
     * @param sw Referencia al Sw
     * @param grupo Referencia del grupo al que pertenece
     */
    public Result(CVE vulnerabilidad, Software sw, String grupo) {
        this.vulnerabilidad = vulnerabilidad;
        this.sw = sw;
        this.grupo = grupo;
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

    /**
     * Getter
     *
     * @return lista de software vulnerable
     */
    public List<Software> getSwList() {
        return swList;
    }

    /**
     * Setter
     *
     * @param swList lista de software vulnerable
     */
    public void setSwList(List<Software> swList) {
        this.swList = swList;
    }

    /**
     * GETTER
     *
     * @return metodo para obtener el grupo del resultado
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * Setter
     *
     * @param grupo Método para establecer el grupo del resultado
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * Getter
     *
     * @return Método para obtener la lista de grupos del resultado
     */
    public List<String> getGruposList() {
        return gruposList;
    }

    /**
     * Setter
     *
     * @param gruposList método para establecer la lista de grupos del resultado
     */
    public void setGruposList(List<String> gruposList) {
        this.gruposList = gruposList;
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
        if (this.vulnerabilidad != other.vulnerabilidad && (this.vulnerabilidad == null || !this.vulnerabilidad.equals(other.vulnerabilidad))) {
            return false;
        }
        return true;
    }

    /*
     @Override
     public String toString() {
     //return "La vulnerabilidad: " + vulnerabilidad.getName() + " puede afectar al sw: " + sw.getNombre() + "\nDetalle: " + vulnerabilidad.getDescription();
     return vulnerabilidad.getName() + "/" + sw.getNombre();
     }
     */
    @Override
    public String toString() {
        return "Result{" + "vulnerabilidad=" + vulnerabilidad + ", sw=" + sw + ", swList=" + swList + ", gruposList=" + gruposList + '}';
    }

}
