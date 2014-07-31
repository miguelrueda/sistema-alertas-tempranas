package mx.org.banxico.sisal.scheduled;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
    
    private static final Logger LOG = Logger.getLogger(JobScheduler.class.getName());
    private Scheduler sched;
    private Trigger trigger;
    private static final String diarioALasDoce = "0 10 12 * * ? *";
    private static final String aCadaMinuto = "0 0/1 * * * ? *";
    private static final String cadaCinco = "0 0/2 * * * ? *";
    private static final String cadaSeisHoras = "0 0 0/6 * * ? *";
    private static final String cadaOchoHoras = "0 0 0/8 * * ? *";
    
    public JobScheduler() {
        
    }
    
    public void start() {
        try {
            LOG.log(Level.INFO, "Iniciando el scheduler: {0}", new Date());
            JobKey updateKey = new JobKey("updateJob", "group1");
            JobDetail updateJob = JobBuilder
                    .newJob(ActualizarSistema.class)
                    .withIdentity(updateKey)
                    .build();
            trigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cadaOchoHoras))
                    .build();
            JobKey scanKey = new JobKey("scanKey", "group1");
            JobDetail scanJob = JobBuilder
                    .newJob(Escanear.class)
                    .withIdentity(scanKey)
                    .build();
            Trigger anotherTrigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cadaSeisHoras))
                    .build();
            sched = new StdSchedulerFactory().getScheduler();
            sched.start();
            sched.scheduleJob(updateJob, trigger);
            sched.scheduleJob(scanJob, anotherTrigger);
        } catch (SchedulerException ex) {
            LOG.log(Level.SEVERE, "ocurrio un error al instanciar el scheduler: {0}", ex.getMessage());
        }
    }

    public void stop() {
        try {
            LOG.log(Level.INFO, "finalizando el scheduler. . .");
            sched.shutdown();
        } catch (SchedulerException ex) {
            LOG.log(Level.INFO, "ocurrio un error al terminar el scheduler: {0}", ex.getMessage());
        }
    }
    
}
