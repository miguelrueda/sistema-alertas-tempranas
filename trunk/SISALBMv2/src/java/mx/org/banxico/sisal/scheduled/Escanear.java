package mx.org.banxico.sisal.scheduled;

import mx.org.banxico.sisal.scanner.ScannerBean;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Set;
import mx.org.banxico.sisal.scanner.Result;

/**
 * Clase que se encarga de realizar el escaneo de vulnerabilidades
 *
 * @author t41507
 * @version 04082014
 */
public class Escanear implements Job {
    
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());
    /**
     * Bean de escaneo
     */
    private ScannerBean scanner;

    /**
     * Método que se encarga de ejecutar la tarea
     *
     * @param jec contexto de ejecución
     * @throws JobExecutionException cuando ocurre un error al ejecutar la tarea
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LOG.log(Level.INFO, "Iniciando Tarea de Escaneo: {0}", jec.getFireTime());
        //Iniciar el bean
        scanner = new ScannerBean();
        //Realizr el escaneo
        Set<Result> resultados = scanner.doRecentScan();
        LOG.log(Level.INFO, "El resultado tiene: {0} elementos!!", resultados.size());
        //doEmail();
        LOG.log(Level.INFO, "El siguiente escaneo se realizar\u00e1 en: {0}", jec.getNextFireTime());
    }
    
}
