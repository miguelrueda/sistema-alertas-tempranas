package mx.org.banxico.sisal.entities;

public class VulnerabilidadReferencia {

    private String idVulnerabilidad;
    private Integer idReferencia;
    private String fuente;
    private String url;

    public VulnerabilidadReferencia() {
    }

    public VulnerabilidadReferencia(String idVulnerabilidad, Integer idReferencia, String fuente, String url) {
        this.idVulnerabilidad = idVulnerabilidad;
        this.idReferencia = idReferencia;
        this.fuente = fuente;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdVulnerabilidad() {
        return idVulnerabilidad;
    }

    public void setIdVulnerabilidad(String idVulnerabilidad) {
        this.idVulnerabilidad = idVulnerabilidad;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    @Override
    public String toString() {
        return "VulnerabilidadReferencia{" + "idVulnerabilidad=" + idVulnerabilidad + ", idReferencia=" + idReferencia + ", fuente=" + fuente + ", url=" + url + '}';
    }

}
