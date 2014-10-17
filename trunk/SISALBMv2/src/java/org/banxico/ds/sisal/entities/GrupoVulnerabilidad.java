package org.banxico.ds.sisal.entities;

import java.util.Date;
import java.util.Objects;

public class GrupoVulnerabilidad implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private Grupo grupo;
    private Vulnerabilidad vulnerabilidad;
    private int afecta;
    private int resuelto;
    private Date fechaSolucion;
    private double avance;

    public GrupoVulnerabilidad() {
    }

    public GrupoVulnerabilidad(Grupo grupo, Vulnerabilidad vulnerabilidad) {
        this.grupo = grupo;
        this.vulnerabilidad = vulnerabilidad;
    }

    public GrupoVulnerabilidad(Grupo grupo, Vulnerabilidad vulnerabilidad, int afecta, int resuelto, Date fechaSolucion, double avance) {
        this.grupo = grupo;
        this.vulnerabilidad = vulnerabilidad;
        this.afecta = afecta;
        this.resuelto = resuelto;
        this.fechaSolucion = fechaSolucion;
        this.avance = avance;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public int getAfecta() {
        return afecta;
    }

    public void setAfecta(int afecta) {
        this.afecta = afecta;
    }

    public int getResuelto() {
        return resuelto;
    }

    public void setResuelto(int resuelto) {
        this.resuelto = resuelto;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public double getAvance() {
        return avance;
    }

    public void setAvance(double avance) {
        this.avance = avance;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.grupo);
        hash = 41 * hash + Objects.hashCode(this.vulnerabilidad);
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
        final GrupoVulnerabilidad other = (GrupoVulnerabilidad) obj;
        if (!Objects.equals(this.grupo, other.grupo)) {
            return false;
        }
        if (!Objects.equals(this.vulnerabilidad, other.vulnerabilidad)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{'grupo':'" + grupo.toString() + "', 'vulnerabilidad':'" + vulnerabilidad.toString() + "', 'afecta':'" + afecta + "', 'resuelto':'" + resuelto + "', 'fechaSolucion':'" + fechaSolucion + "', 'avance':'" + avance + "'}";
    }

}
