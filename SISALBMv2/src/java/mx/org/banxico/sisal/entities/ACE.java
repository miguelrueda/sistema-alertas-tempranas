package mx.org.banxico.sisal.entities;

public class ACE implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    private Integer idSoftware;
    private String SID;

    public ACE() {
    }

    public ACE(Integer idSoftware, String SID) {
        this.idSoftware = idSoftware;
        this.SID = SID;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(Integer idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    @Override
    public String toString() {
        return "ACE{" + "idSoftware=" + idSoftware + ", SID=" + SID + '}';
    }

}
