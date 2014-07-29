package scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MyScheduler {

    private static final Logger LOG = Logger.getLogger(MyScheduler.class.getName());
    private Scheduler sched;
    private Trigger trigger;

    public void start() {
        try {
            JobDetail jDetail = JobBuilder.newJob(AddJob.class).withIdentity("addJob").build();
            trigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(30)
                            //.withIntervalInHours(24)
                            .repeatForever())
                    .build();
            sched = new StdSchedulerFactory().getScheduler();
            sched.getListenerManager().addJobListener(new SimpleJobListener());
            sched.start();
            sched.scheduleJob(jDetail, trigger);
        } catch (SchedulerException e) {
            LOG.log(Level.INFO, "Excepci\u00f3n al iniciar: {0}", e.getMessage());
        }
    }

    public void stop() {
        try {
            sched.shutdown();
        } catch (SchedulerException e) {
            LOG.log(Level.INFO, "Excepci\u00f3n al cerrar: {0}", e.getMessage());
        }
    }

    public Date getNextFireTime() {
        return trigger.getNextFireTime();
    }

    public Date getPreviousFireTime() {
        return trigger.getPreviousFireTime();
    }
    
}
