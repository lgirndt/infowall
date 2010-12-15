package infowall.domain.service.scheduler;

import infowall.domain.model.Dashboard;
import infowall.domain.service.DashboardImportListener;

/**
 *
 */
public class SchedulerDashboardImportListener implements DashboardImportListener{

    private final SchedulerService schedulerService;

    public SchedulerDashboardImportListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public void onDashboardReloaded(Dashboard dashboard) {
        schedulerService.registerDashboardJobs(dashboard.getId());
    }
}
