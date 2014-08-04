package mx.org.banxico.sisal.scheduled;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Date;
import java.util.List;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * Clase que se encarga de agendar las tareas
 *
 * @author t41507
 * @version 04082014
 */
public class JobScheduler {

    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(JobScheduler.class.getName());
    /**
     * Atributos del Scheduler
     */
    private Scheduler sched;
    private Trigger trigger;
    /**
     * Fechas posibles para ejecutar las tareas
     */
    private static final String aCadaMinuto = "0 0/1 * * * ? *";
    private static final String cadaCinco = "0 0/2 * * * ? *";
    private static final String cadaSeisHoras = "0 0 0/6 * * ? *";
    private static final String cadaOchoHoras = "0 0 0/8 * * ? *";
    private static final String diarioALasDiez = "0 10 10 * * ? *";
    private static final String diarioALasDiezTreinta = "0 30 10 * * ? *";
    private static final String diarioALasDoce = "0 0 12 * * ? *";
    private static final String mensual = "0 0 8 1 1/1 ? *";

    /**
     * Constructor sin parametros
     */
    public JobScheduler() {

    }

    /**
     * Método que se encarga de inicializar las tareas
     */
    public void start() {
        try {
            LOG.log(Level.INFO, "Iniciando el scheduler: {0}", new Date());
            //LLave para la tarea 1
            JobKey updateKey = new JobKey("Actualizar Sistema", "group1");
            JobDetail updateJob = JobBuilder
                    .newJob(ActualizarSistema.class)
                    .withIdentity(updateKey)
                    .build();
            //Trigger para la tarea 1
            trigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(diarioALasDiez))
                    .build();
            //LLave para la tarea 2
            JobKey scanKey = new JobKey("Escaneo", "group1");
            JobDetail scanJob = JobBuilder
                    .newJob(Escanear.class)
                    .withIdentity(scanKey)
                    .build();
            //Trigger para la tarea 2
            Trigger anotherTrigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(diarioALasDiezTreinta))
                    .build();
            //LLave para la tarea 3
            JobKey monthlyKey = new JobKey("Reporte mensual", "group1");
            JobDetail monthlyReportJob = JobBuilder
                    .newJob(GenerarReporteMensual.class)
                    .withIdentity(monthlyKey)
                    .build();
            //Trigger para la tarea 3
            Trigger thirdTrigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(mensual))
                    .build();
            sched = new StdSchedulerFactory().getScheduler();
            //Iniciar el scheduler
            sched.start();
            //Agregar las tareas al cheduler
            sched.scheduleJob(updateJob, trigger);
            sched.scheduleJob(scanJob, anotherTrigger);
            sched.scheduleJob(monthlyReportJob, thirdTrigger);
        } catch (SchedulerException ex) {
            LOG.log(Level.SEVERE, "ocurrio un error al instanciar el scheduler: {0}", ex.getMessage());
        }
    }

    /**
     * Método que se encarga de terminar las tareas
     */
    public void stop() {
        try {
            LOG.log(Level.INFO, "finalizando el scheduler. . .");
            sched.shutdown();
        } catch (SchedulerException ex) {
            LOG.log(Level.INFO, "ocurrio un error al terminar el scheduler: {0}", ex.getMessage());
        }
    }
    
    /**
     * Método que permite visualizar la información de las tareas
     * 
     * @throws SchedulerException cuando no se encuentra información del scheduler
     */
    private void verInformacion() throws SchedulerException {
        for (String grupo : sched.getJobGroupNames()) {
            for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(grupo))) {
                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                List<Trigger> triggers = (List<Trigger>) sched.getTriggersOfJob(jobKey);
                Date nextFireTime = triggers.get(0).getNextFireTime();
                System.out.println("[jobName]: " + jobName + " [groupName]: " + jobGroup + " [NFT]: " + nextFireTime);
            }
        }
    }

    /**
     * Método getter para obtener el objeto scheduler
     *
     * @return instancia de scheduler
     */
    public Scheduler getSched() {
        return sched;
    }

}
