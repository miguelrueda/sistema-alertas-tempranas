package org.banxico.ds.sisal.ejb;

import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Schedule;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import org.banxico.ds.sisal.dao.SourcesDAO;
import org.banxico.ds.sisal.entities.FuenteApp;

@Stateless(name = "ActualizarCPE", mappedName = "ejb/ds/sisalbm/ActualizarCPE")
public class ActualizarCPE implements ActualizarCPELocal {
    
    /**
     * Inyección del recurso del servicio de tiempo
     */
    @Resource
    TimerService timerService;
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(ActualizarCPE.class.getName());
    /**
     * Atributos que definen la hora de la actualización
     */
    private static final int START_HOUR = 8;
    private static final int START_MINUTES = 0;
    private static final int START_SECONDS = 0;
    private static final int INTERVAL_IN_MINUTES = 1440 * 15;
    /**
     * Atributos del Bean
     */
    private static final String descripcion = "Actualización de CPE: catalogo de productos.";
    private SourcesDAO sourcesdao;
    private Date ultimaEjecucion;

    /**
     * Método que se encarga de establecer el tiempo para actualización
     */
    @Override
    public void setTimer() {
        try {
            stopTimer();
            Calendar initialExpiration = Calendar.getInstance();
            initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
            initialExpiration.set(Calendar.MINUTE, START_MINUTES);
            initialExpiration.set(Calendar.SECOND, START_SECONDS);
            long duracion = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
            LOG.log(Level.INFO, "ActualizarCPE#setTimer() - Timer de actualizaci\u00f3n de cpe creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
            Collection<Timer> timers = timerService.getTimers();
            if (timers.size() > 0) {
                return;
            }
            timerService.createTimer(initialExpiration.getTime(), duracion, descripcion);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "ActualizarCPE#setTimer() - Ocurrio un error al establecer los argumentos del Timer: {0}", e.getMessage());
        } catch (IllegalStateException e) {
            LOG.log(Level.INFO, "ActualizarCPE#setTimer() - Ocurrio un error, estado ilegal del Timer: {0}", e.getMessage());
        } catch (EJBException e) {
            LOG.log(Level.INFO, "ActualizarCPE#setTimer() - Ocurrio un error con el EJB: {0}", e.getMessage());
        }
    }

    /**
     * Método que se utiliza para detener los temporizadores
     */
    @Override
    public void stopTimer() {
        try {
            Collection<Timer> timers = timerService.getTimers();
            if (timers != null) {
                for (Timer timer : timers) {
                    timer.cancel();
                    LOG.log(Level.INFO, "ActualizarCPE#stopTimer() - Timer: {0} cancelado", timer);
                }
            }
        } catch (IllegalStateException e) {
            LOG.log(Level.INFO, "ActualizarCPE#stopTimer() - Ocurrio un error, estado ilegal del Timer: {0}", e.getMessage());
        } catch (EJBException e) {
            LOG.log(Level.INFO, "ActualizarCPE#stopTimer() - Ocurrio un error con el EJB: {0}", e.getMessage());
        }
    }
    
    /**
     * Método que se encarga de realizar la actualización
     *
     * @param timer objeto de tipo Timer
     */
    @Timeout
    public void actualizarCPE(Timer timer) {
        LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - Ejecutando el bean: {0}/{1}", new Object[]{timer.getInfo(), new Date()});
        this.setUltimaEjecucion(new Date());
        sourcesdao = new SourcesDAO();
        FuenteApp fuentecpe = sourcesdao.obtenerFuentePorId(5);
        LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - Fuente obtenida: {0}", fuentecpe.toString());
        Calendar recent = Calendar.getInstance();
        Calendar back = Calendar.getInstance();
        Date ultimaAct = sourcesdao.obtenerFechaActualizacion(fuentecpe.getId().toString());
        LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - Ultima actualización: {0}", ultimaAct.toString());
        Date ahora = new Date();
        recent.setTime(ahora);
        back.setTime(ultimaAct);
        LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - Tiempos: {0} / {1}", new Object[]{recent.getTime().toString(), back.getTime().toString()});
        if (!(back.get(Calendar.DAY_OF_MONTH) == recent.get(Calendar.DAY_OF_MONTH))) {
            LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - Actualizando cpe, descargando: {0} - {1}", new Object[]{fuentecpe.getUrl(), new Date()});
            boolean flag = sourcesdao.descargarFuente(fuentecpe.getId().toString(), fuentecpe.getUrl());
            if (flag) {
                LOG.log(Level.INFO, "ActualizarCPE#actualizarCPE() - La fuente {0} se actualizo exitosamente!", fuentecpe.getId());
            }
            long delay = 30000L;
        }
    }
    
    /**
     * Método que se encarga de retornar la descripción de la tarea
     *
     * @return cadena con la descripción
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que se encarga de retornar información de la siguiente ejecución
     * de la tarea
     *
     * @return fecha de la siguiente ejecución
     */
    @Override
    public Date getNextFireTime() {
        Collection<Timer> timers = timerService.getTimers();
        Date next = null;
        for (Timer timer : timers) {
            next = timer.getNextTimeout();
        }
        return next;
    }

    /**
     * Getter
     *
     * @return fecha con la ultima ejecucion
     */
    @Override
    public Date getUltimaEjecucion() {
        if (ultimaEjecucion != null) {
            return ultimaEjecucion;
        }
        return null;
    }

    /**
     * Setter
     *
     * @param ultimaEjecucion fecha con la ultima ejecucion
     */
    public void setUltimaEjecucion(Date ultimaEjecucion) {
        this.ultimaEjecucion = ultimaEjecucion;
    }
    
}