package org.banxico.ds.sisal.scheduled;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Set;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Clase que se encarga de generar el reporte Mensual
 *
 * @author t41507
 * @version 04082014
 */
public class GenerarReporteMensual implements Job {
    
    /**
     * Atributo de Logger
     */
    private static final Logger LOG = Logger.getLogger(GenerarReporteMensual.class.getName());
    /**
     * Bean de Scanner
     */
    private ScannerBean scanner;

    /**
     * Método que se encarga de ejecutar la tarea
     *
     * @param jec contexto de ejecución
     * @throws JobExecutionException cuando no se puede ejecutar la tarea
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LOG.log(Level.INFO, "Iniciando tarea de reporte mensual: {0}", jec.getFireTime());
        //Inicialización del bean
        scanner = new ScannerBean();
        //Realizar Escaneo Mensual
        Set<Result> resultados = scanner.doMonthlyScan();
        LOG.log(Level.INFO, "El resultado tiene: {0} elementos!!", resultados.size());
        //do Generar y +Enviar reporte mensual
    }
    
}