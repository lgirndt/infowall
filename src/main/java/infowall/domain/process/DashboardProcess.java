package infowall.domain.process;

import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import infowall.domain.service.DashboardImporter;
import infowall.domain.service.scheduler.DashboardImportException;
import infowall.web.services.errorhandling.ErrorNotifier;
import org.ektorp.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DashboardProcess {

    private final Logger logger = LoggerFactory.getLogger(DashboardProcess.class);

    private final DashboardRepository dashboardRepository;
    private final DashboardImporter dashboardImporter;

    @Autowired
    public DashboardProcess(
            DashboardRepository dashboardRepository,
            DashboardImporter dashboardImporter) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardImporter = dashboardImporter;
    }

    public List<Dashboard> listAllDashboards(){
        return dashboardRepository.getAll();
    }

    public Dashboard getDashboard(String dashboardId){
        try {
            return dashboardRepository.get(dashboardId);
        }catch(DocumentNotFoundException e){
            return null;
        }
    }

    public Dashboard reloadDashboard(String dashboardId, ErrorNotifier errorNotifier){
        try {
            if(!dashboardRepository.contains(dashboardId)){
                errorNotifier.addError("Dashboard '"  + dashboardId + "' does not exist.");
                return null;
            }
            dashboardImporter.importDashboard(dashboardId);
        } catch (DashboardImportException e) {
            logger.error("Cannot reload Dashboard.",e);
            errorNotifier.addError("Cannot reload dashboard: " + e.getMessage());
        }
        return dashboardRepository.get(dashboardId);
    }
}
