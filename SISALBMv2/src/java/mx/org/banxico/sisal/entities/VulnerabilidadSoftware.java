package mx.org.banxico.sisal.entities;

public class VulnerabilidadSoftware implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private String idVulnerabilidad;
    private Integer idSoftware;

    public VulnerabilidadSoftware() {
    }

    public VulnerabilidadSoftware(String idVulnerabilidad, Integer idSoftware) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.idSoftware = idSoftware;
    }

    public Integer getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getIdVulnerabilidad() {
        return idVulnerabilidad;
    }

    public void setIdVulnerabilidad(String idVulnerabilidad) {
        this.idVulnerabilidad = idVulnerabilidad;
    }

    @Override
    public String toString() {
        return "VulnerabilidadSoftware{" + "idVulnerabilidad=" + idVulnerabilidad + ", idSoftware=" + idSoftware + '}';
    }

}
