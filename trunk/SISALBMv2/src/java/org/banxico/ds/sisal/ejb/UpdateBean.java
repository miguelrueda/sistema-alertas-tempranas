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

@Stateless
public class UpdateBean implements UpdateBeanLocal {
    
    @Resource
    TimerService timerService;
    private static final Logger LOG = Logger.getLogger(UpdateBean.class.getName());
    private static final int START_HOUR = 10;
    private static final int START_MINUTES = 30;
    private static final int START_SECONDS = 0;
    private static final int INTERVAL_IN_MINUTES = 360;
    private static final String descripcion = "Actualizaciones";
    private SourcesDAO sourcesdao;

    @Override
    public void setTimer() {
        LOG.log(Level.INFO, "Estableciendo el timer de actualización");
        stopTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long duration = new Integer(INTERVAL_IN_MINUTES).longValue() * 60 * 1000;
        LOG.log(Level.INFO, "Timer de actualizaci\u00f3n creado: {0} con un intervalo de: {1}", new Object[]{initialExpiration.getTime(), INTERVAL_IN_MINUTES});
        timerService.createTimer(initialExpiration.getTime(), duration, descripcion);
    }

    @Override
    public void stopTimer() {
        Collection<Timer> timers = timerService.getTimers();
        if (timers != null) {
            for (Iterator it = timers.iterator(); it.hasNext();) {
                Timer t = (Timer) it.next();
                t.cancel();
                LOG.log(Level.INFO, "Timer: {0} cancelado.", t);
            }
        }
    }
    
    @Timeout
    public void doUpdate(Timer timer) {
        LOG.log(Level.INFO, "Ejecutando el Bean: {0} / {1}", new Object[]{timer.getInfo(), new Date()});
        sourcesdao = new SourcesDAO();
        List<FuenteApp> fuentes = sourcesdao.obtenerFuentes();
        for (FuenteApp fuente : fuentes) {
            LOG.log(Level.INFO, "Actualizando la fuente: {0} - Descargando: {1} - {2}", new Object[]{fuente.getId().toString(), fuente.getUrl(), new Date()});
            sourcesdao.descargarFuente(fuente.getId().toString(), fuente.getUrl());
            long delay = 30000L;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.log(Level.INFO, "Ocurrio un error al ejecutar la espera!!!");
            }
        }
        //Random rd = new Random();
        //int suma = rd.nextInt(1000) + rd.nextInt(1100);
        //LOG.log(Level.INFO, "Ejecutando el timer {0} - tiempo: {1}", new Object[]{timer.getInfo(), new Date()});
        //LOG.log(Level.INFO, "[Suma]El Resultado es: {0}", suma);
        LOG.log(Level.INFO, "La siguiente actualización se ejecutará el: {0}", timer.getNextTimeout());
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public Date getNextFireTime() {
        Collection<Timer> timers = timerService.getTimers();
        Date next = null;
        for (Timer timer : timers) {
            next = timer.getNextTimeout();
        }
        return next;
    }
   
}