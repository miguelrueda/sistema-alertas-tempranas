package src.test;

import cve.entidades.CVE;
import java.util.List;
import java.util.Objects;

public class Result implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private CVE vulnerabilidad;
    private Software sw;
    private List<Software> swList;

    public Result() {
    }

    public Result(CVE vulnerabilidad, Software sw) {
        this.vulnerabilidad = vulnerabilidad;
        this.sw = sw;
    }

    public Result(CVE vulnerabilidad, List<Software> swList) {
        this.vulnerabilidad = vulnerabilidad;
        this.swList = swList;
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
        return vulnerabilidad.getName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.vulnerabilidad);
        hash = 59 * hash + Objects.hashCode(this.sw);
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
        if (!Objects.equals(this.vulnerabilidad, other.vulnerabilidad)) {
            return false;
        }
        return true;
    }

    public List<Software> getSwList() {
        return swList;
    }

    public void setSwList(List<Software> swList) {
        this.swList = swList;
    }

}
