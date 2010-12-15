package infowall.setup;

import infowall.domain.service.DashboardImporter;
import infowall.domain.service.scheduler.SchedulerDashboardImportListener;
import infowall.domain.service.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SetupBean implements InitializingBean{

    private final Logger logger = LoggerFactory.getLogger(SetupBean.class);

    private final DashboardImporter dashboardImporter;
    private final SchedulerService schedulerService;

    @Autowired
    public SetupBean(DashboardImporter dashboardImporter,
                     SchedulerService schedulerService) {
        this.dashboardImporter = dashboardImporter;
        this.schedulerService = schedulerService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Setup Application.");

        dashboardImporter.addListener(new SchedulerDashboardImportListener(schedulerService));

        dashboardImporter.importAllDashboards();
    }
}
