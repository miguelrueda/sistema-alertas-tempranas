package jpa.entities;

import java.util.Date;
import java.util.logging.Logger;

public class Scan implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(Scan.class.getName());
    private Integer id;
    private Date scanDate;
    //Lista a Escanear

    public Scan() {
    }

    public Scan(Integer id, Date scanDate) {
        this.id = id;
        this.scanDate = scanDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

}
