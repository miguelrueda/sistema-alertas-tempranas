package org.banxico.ds.sisal.ejb;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface ActualizarCPELocal {
     
    /**
     * Método para establecer el temporizador
     */
    public void setTimer();
    
    /**
     * Método para detener el temporizador
     */
    public void stopTimer();
    
    /**
     * Método que retorna la descripción de la tarea
     *
     * @return cadena con la descripción
     */
    public String getDescripcion();
    
    /**
     * Método que retorna la fecha de la siguiente ejecución
     * 
     * @return fecha con al siguiente ejecución
     */
    public Date getNextFireTime();
    
    /**
     * Método que retorna la fecha de la ultima ejecución
     *
     * @return fecha de la ultima ejecución
     */
    public Date getUltimaEjecucion();
}