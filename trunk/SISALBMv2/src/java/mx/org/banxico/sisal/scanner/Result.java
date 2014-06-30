package mx.org.banxico.sisal.scanner;

import mx.org.banxico.sisal.entities.Software;
import mx.org.banxico.sisal.parser.entidades.CVE;

public class Result {

    private CVE vulnerabilidad;
    private Software sw;

    public Result() {
    }

    public Result(CVE vulnerabilidad, Software sw) {
        this.vulnerabilidad = vulnerabilidad;
        this.sw = sw;
    }

    public CVE getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(CVE vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public Software getSw() {
        return sw;
    }

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
