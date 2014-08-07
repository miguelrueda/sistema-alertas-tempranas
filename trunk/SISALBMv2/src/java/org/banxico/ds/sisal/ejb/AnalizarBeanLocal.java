package org.banxico.ds.sisal.ejb;

import java.util.Date;
import javax.ejb.Local;

/**
 * Interface que tiene las operaciones para realizar el analisis de vulnerabilidades
 * 
 * @author t41507
 * @version 07.08.2014
 */
@Local
public interface AnalizarBeanLocal {

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
}
