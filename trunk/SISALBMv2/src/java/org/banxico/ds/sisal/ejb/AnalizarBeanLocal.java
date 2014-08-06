package org.banxico.ds.sisal.ejb;

import java.util.Date;
import javax.ejb.Local;

@Local
public interface AnalizarBeanLocal {

    public void setTimer();

    public void stopTimer();

    public String getDescripcion();

    public Date getNextFireTime();
}
