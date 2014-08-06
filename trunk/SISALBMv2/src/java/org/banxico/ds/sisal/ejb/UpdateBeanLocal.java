package org.banxico.ds.sisal.ejb;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface UpdateBeanLocal {
    
    public void setTimer();
    
    public void stopTimer();
    
    public String getDescripcion();
    
    public Date getNextFireTime();
}
