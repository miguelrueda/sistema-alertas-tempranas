package org.banxico.ds.sisal.ejb;

import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.ejb.Timeout;
import org.banxico.ds.sisal.dao.SourcesDAO;
import org.banxico.ds.sisal.entities.FuenteApp;

/**
 * Bean que realiza la tarea de actualización
 *
 * @author t41507
 * @version 07.08.2014
 */
@Stateless(name = "UpdateBean", mappedName = "ejb/ds/sisalbm/UpdateBean")
public class UpdateBean implements UpdateBeanLocal {
    
    /**
     * Inyección del recurso del servicio de tiempo
     */
    @Resource
    TimerService timerService;
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(UpdateBean.class.getName());
    /**
     * Atributos que definen la hora de la actualización
     */
    private static final int START_HOUR = 10;
    private static final int START_MINUTES = 30;
    private static final int START_SECONDS = 0;
    private static final int INTERVAL_IN_MINUTES = 1440; //1 dia
    /**
     * Atributos del Bean
     */
    private static final String descripcion = "Actualizaciones";
    private SourcesDAO sourcesdao;
    private Date ultimaEjecucion;

    /**
     * Método que se encarga de establecer el tiempo para actualización
     */
    @Override
    public void setTimer() {
        stopTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long duration = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
        LOG.log(Level.INFO, "UpdateBean#setTimer() - Timer de actualizaci\u00f3n creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
        Collection<Timer> timers = timerService.getTimers();
        if (timers.size() > 0) {
            return;
        }
        timerService.createTimer(initialExpiration.getTime(), duration, descripcion);
    }

    /**
     * Método que se utiliza para detener los temporizadores
     */
    @Override
    public void stopTimer() {
        Collection<Timer> timers = timerService.getTimers();
        if (timers != null) {
            for (Timer t : timers) {
                t.cancel();
                LOG.log(Level.INFO, "UpdateBean#stopTimer() - Timer: {0} cancelado.", t);
            }
        }
    }
    
    /**
     * Método que se encarga de realizar la actualización
     *
     * @param timer objeto de tipo Timer
     */
    @Timeout
    public void doUpdate(Timer timer) {
        LOG.log(Level.INFO, "UpdateBean#doUpdate() - Ejecutando el Bean: {0} / {1}", new Object[]{timer.getInfo(), new Date()});
        this.setUltimaEjecucion(new Date());
        sourcesdao = new SourcesDAO();
        List<FuenteApp> fuentes = sourcesdao.obtenerFuentes();
        for (FuenteApp fuente : fuentes) {
            LOG.log(Level.INFO, "UpdateBean#doUpdate() - Actualizando la fuente: {0} - Descargando: {1} - {2}", new Object[]{fuente.getId().toString(), fuente.getUrl(), new Date()});
            sourcesdao.descargarFuente(fuente.getId().toString(), fuente.getUrl());
            long delay = 30000L;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.log(Level.INFO, "UpdateBean#doUpdate() - Ocurrio un error al ejecutar la espera!!!");
            }
        }
        LOG.log(Level.INFO, "UpdateBean#doUpdate() - La siguiente actualización se ejecutará el: {0}", timer.getNextTimeout());
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
     * Método que se encarga de retornar información de la siguiente ejecución de la tarea
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