package infowall.domain.service.scheduler;

import infowall.domain.model.Dashboard;
import infowall.domain.model.DashboardItem;
import infowall.domain.persistence.DashboardRepository;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 *
 */
@Service
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    
    private final DashboardRepository dashboardRepository;
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(
            DashboardRepository dashboardRepository,
            Scheduler scheduler)  {
        this.dashboardRepository = dashboardRepository;
        this.scheduler = scheduler;
    }

    public void registerDashboardJobs(String dashboardId){

        String group = dashboardId;
        Dashboard dashboard = dashboardRepository.get(dashboardId);

        logger.info("Register jobs of Dashboard: " + dashboardId);

        try {
            scheduler.pauseJobGroup(group);
            for(DashboardItem item : dashboard.getItems()){
                if(item.getScheduler() != null){
                    registerJobForItem(group, item);
                }
            }
            scheduler.resumeJobGroup(group);

        } catch (SchedulerException e) {
            logger.error("Cannot register jobs for dashboard " + dashboardId,e);
        } catch (ParseException e) {
            logger.error("Cannot register jobs for dashboard " + dashboardId,e);
        }
    }

    private void registerJobForItem(String group, DashboardItem item) throws SchedulerException, ParseException {
        logger.info("Setup Job for DashboardItem " + item.getName());

        String jobName = item.getName();
        JobDetail job = new JobDetail(jobName,group, ScriptExecutingJob.class);
        scheduler.addJob(job,true);
        
        String triggerName = jobName + "-trigger";
        CronTrigger cronTrigger = new CronTrigger(triggerName,group,jobName,group, item.getScheduler());
        scheduler.scheduleJob(cronTrigger);
    }

    public void registerAllDashboardJobs(){
        for(Dashboard dashboard : dashboardRepository.getAll()){
            registerDashboardJobs(dashboard.getId());
        }
    }
}
