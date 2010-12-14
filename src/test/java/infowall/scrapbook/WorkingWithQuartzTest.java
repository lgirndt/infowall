package infowall.scrapbook;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 *
 */
public class WorkingWithQuartzTest {

    public static class SimpleJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("hello world");
        }
    }

    @Test
    public void simpleQuartz() throws SchedulerException, ParseException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        String group1 = "group1";
        String job1 = "job1";
        JobDetail job = new JobDetail(job1, group1,SimpleJob.class);
        CronTrigger trigger = new CronTrigger("trigger1","triggerGroup",job1,group1,"0/2 * * * * ?");
        sched.addJob(job,true);
        sched.scheduleJob(trigger);

        sched.start();
        Thread.sleep(100000);
    }
}
