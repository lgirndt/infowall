package infowall.domain.process;

import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DashboardProcess {

    private final DashboardRepository dashboardRepository;

    @Autowired
    public DashboardProcess(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<Dashboard> listAllDashboards(){
        return dashboardRepository.getAll();
    }
}
