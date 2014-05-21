package cveparser;

/**
 * Clase que representa a la entidad CVSS //Common Vulnerability Scoring System
 * (CVSS) provides a universal open and standardized method for rating IT
 * vulnerabilities.
 *
 * @author t41507
 * @version 21.05.2014
 */
public class CVSS {

    public String score;
    public String vector;

    public CVSS() {
    }

    public CVSS(String score, String vector) {
        this.score = score;
        this.vector = vector;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "CVSS{" + "score=" + score + ", vector=" + vector + '}';
    }

}
