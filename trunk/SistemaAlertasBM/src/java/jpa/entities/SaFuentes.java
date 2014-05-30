package jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author t41507
 */
@Entity
@Table(name = "SA_FUENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaFuentes.findAll", query = "SELECT s FROM SaFuentes s"),
    @NamedQuery(name = "SaFuentes.findByFntId", query = "SELECT s FROM SaFuentes s WHERE s.fntId = :fntId"),
    @NamedQuery(name = "SaFuentes.findByFntName", query = "SELECT s FROM SaFuentes s WHERE s.fntName = :fntName"),
    @NamedQuery(name = "SaFuentes.findByFntUrl", query = "SELECT s FROM SaFuentes s WHERE s.fntUrl = :fntUrl"),
    @NamedQuery(name = "SaFuentes.findByFntFechaModif", query = "SELECT s FROM SaFuentes s WHERE s.fntFechaModif = :fntFechaModif")})
public class SaFuentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FNT_ID")
    private Integer fntId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FNT_NAME")
    private String fntName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FNT_URL")
    private String fntUrl;
    @Column(name = "FNT_FECHA_MODIF")
    @Temporal(TemporalType.DATE)
    private Date fntFechaModif;

    public SaFuentes() {
    }

    public SaFuentes(Integer fntId) {
        this.fntId = fntId;
    }

    public SaFuentes(Integer fntId, String fntName, String fntUrl) {
        this.fntId = fntId;
        this.fntName = fntName;
        this.fntUrl = fntUrl;
    }

    public SaFuentes(Integer fntId, String fntName, String fntUrl, Date fntFechaModif) {
        this.fntId = fntId;
        this.fntName = fntName;
        this.fntUrl = fntUrl;
        this.fntFechaModif = fntFechaModif;
    }

    public Integer getFntId() {
        return fntId;
    }

    public void setFntId(Integer fntId) {
        this.fntId = fntId;
    }

    public String getFntName() {
        return fntName;
    }

    public void setFntName(String fntName) {
        this.fntName = fntName;
    }

    public String getFntUrl() {
        return fntUrl;
    }

    public void setFntUrl(String fntUrl) {
        this.fntUrl = fntUrl;
    }

    public Date getFntFechaModif() {
        return fntFechaModif;
    }

    public void setFntFechaModif(Date fntFechaModif) {
        this.fntFechaModif = fntFechaModif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fntId != null ? fntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaFuentes)) {
            return false;
        }
        SaFuentes other = (SaFuentes) object;
        if ((this.fntId == null && other.fntId != null) || (this.fntId != null && !this.fntId.equals(other.fntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fuente{" + "fntId=" + fntId + ", fntName=" + fntName + ", fntUrl=" + fntUrl + ", fntFechaModif=" + fntFechaModif + '}';
    }

}
