package cve.entidades;

/**
 * Clase que representa a la entidad CVSS //Common Vulnerability Scoring System
 * (CVSS) provides a universal open and standardized method for rating IT
 * vulnerabilities.
 *
 * @author t41507
 * @version 23.05.2014
 */
public class CVSS {
    
    /**
     * Atributo calificación de cvss
     */
    public String score;

    /**
     * Atributo vector de cvss
     */
    public String vector;

    /**
     * Constructor
     */
    public CVSS() {
    }

    /**
     * Constructor
     * @param score calificación
     * @param vector vector de cvss
     */
    public CVSS(String score, String vector) {
        this.score = score;
        this.vector = vector;
    }
    
    /**
     * GETTER
     * @return calificacion
     */
    public String getScore() {
        return score;
    }
    
    /**
     * SETTER
     * 
     * @param score 
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * GETTER
     * 
     * @return vector
     */
    public String getVector() {
        return vector;
    }

    /**
     * SETTER
     * 
     * @param vector 
     */
    public void setVector(String vector) {
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "CVSS{" + "score=" + score + ", vector=" + vector + '}';
    }

}
