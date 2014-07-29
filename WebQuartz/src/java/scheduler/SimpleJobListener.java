package scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class SimpleJobListener implements JobListener {

    @Override
    public String getName() {
        return "SimpleJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jec) {
        System.out.println("jobToBeExecuted: " + jec.getNextFireTime());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jec) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jec, JobExecutionException jee) {
        System.out.println("jobWasExecuted: " + jec.getFireTime());
    }
    
}
