package mx.org.banxico.sisal.scheduled;

import mx.org.banxico.sisal.scanner.ScannerBean;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Set;
import mx.org.banxico.sisal.scanner.Result;

public class Escanear implements Job {
    
    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());
    private ScannerBean scanner;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LOG.log(Level.INFO, "Iniciando Tarea de Escaneo: {0}", jec.getFireTime());
        scanner = new ScannerBean();
        Set<Result> resultados = scanner.doRecentScan();
        LOG.log(Level.INFO, "El resultado tiene: {0} elementos!!", resultados.size());
        //doEmail();
        LOG.log(Level.INFO, "El siguiente escaneo se realizar\u00e1 en: {0}", jec.getNextFireTime());
    }
    
}
