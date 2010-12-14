package infowall.domain.service.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ScriptExecutingJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutingJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Job is executed");
    }
}
