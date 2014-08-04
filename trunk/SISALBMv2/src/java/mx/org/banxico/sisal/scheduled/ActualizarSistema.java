package mx.org.banxico.sisal.scheduled;

import mx.org.banxico.sisal.dao.SourcesDAO;
import mx.org.banxico.sisal.entities.FuenteApp;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.List;
import java.util.Date;

/**
 * Tarea que se encarga de realizar la actualización de las fuentes
 *
 * @author t41507
 * @version 04082014
 */
public class ActualizarSistema implements Job {
    
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(ActualizarSistema.class.getName());
    /**
     * DAO de fuentes para obtener URLS
     */
    private SourcesDAO sourcesDAO;

    /**
     * Método que se encarga de ejecutar la tarea
     *
     * @param jec Contexto de ejecución
     * @throws JobExecutionException cuando ocurre un error dentro de la tarea a ejecutar
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LOG.log(Level.INFO, "Iniciando Tarea de actualizaci\u00f3n: {0}", jec.getFireTime());
        //Inicializar DAO y obtener toodas las fuentes
        sourcesDAO = new SourcesDAO();
        List<FuenteApp> fuentes = sourcesDAO.obtenerFuentes();
        //Para cada fuente obtenida realizar la descarga y dormir por 30 segundos
        for (FuenteApp fuente : fuentes) {
            LOG.log(Level.INFO, "Actualizando la fuente: {0} Descargando: {1} - {2}", new Object[]{fuente.getId().toString(), fuente.getUrl(), new Date()});
            sourcesDAO.descargarFuente(fuente.getId().toString(), fuente.getUrl()); 
            long delay = 30000L;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.log(Level.INFO, "Ocurrio una esperar al ejecutar la espera!!");
            }
        }
        LOG.log(Level.INFO, "La siguiente actualizaci\u00f3n se realizar\u00e1 el: {0}", jec.getNextFireTime());
    }
    
}
