package infowall.domain.service.scheduler;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.process.ScriptExecutorProcess;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 *
 */
public class ScriptExecutingJob implements StatefulJob {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutingJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job " + context.getJobDetail().getFullName() + " executed");

        JobDataMap map = context.getMergedJobDataMap();
        BeanFactory beanFactory = (BeanFactory) map.get("beanFactory");
        DashboardItemRef itemRef = (DashboardItemRef) map.get("itemRef");

        ScriptExecutorProcess scriptExecutorProcess = beanFactory.getBean(ScriptExecutorProcess.class);
        scriptExecutorProcess.execScriptAndStoreOutput(itemRef);
    }
}
