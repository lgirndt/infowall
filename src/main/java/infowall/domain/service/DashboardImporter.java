package infowall.domain.service;

import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import infowall.domain.service.scheduler.DashboardImportException;
import infowall.infrastructure.ConfigRoot;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.asList;

/**
 *
 */
@Service
public class DashboardImporter {

    private final Logger logger = LoggerFactory.getLogger(DashboardImporter.class);

    private final ConfigRoot configRoot;
    private final DashboardRepository dashboardRepository;

    private List<DashboardImportListener> listeners;
    private final ObjectMapper mapper;
    private static final String JSON = ".json";

    @Autowired
    public DashboardImporter(
            ConfigRoot configRoot,
            DashboardRepository dashboardRepository) {

        this.configRoot = configRoot;
        this.dashboardRepository = dashboardRepository;
        this.listeners = new CopyOnWriteArrayList<DashboardImportListener>();
        mapper = new ObjectMapper();
    }

    public void addListeners(Collection<DashboardImportListener> listeners){
        this.listeners.addAll(listeners);
    }

    public void notifyDashboardReloaded(Dashboard dashboard){
        for(DashboardImportListener listener : listeners){
            listener.onDashboardReloaded(dashboard);
        }
    }

    public void importAllDashboards() {
        List<File> files = listJsonFiles();

        for(File file : files){
            importDashboardFromFile(file);
        }
    }

    public void importDashboard(String dashboardId) throws DashboardImportException{
        File baseDir = findDashboardBaseDir();
        if(!baseDir.exists()){
            throw new DashboardImportException(
                    "Cannot import Dashboard, because the configuration directory does not exist.");
        }
        File file = new File(baseDir,dashboardId + JSON);
        if(!file.exists()){
            throw new DashboardImportException(
                    "Cannot import Dashboard, because no config file exists for Dashboard: " + file.getAbsolutePath());
        }
        importDashboardFromFile(file);
    }

    private void importDashboardFromFile(File file) {
        try {
            Dashboard dashboard = convertToDashboard(file);
            dashboardRepository.put(dashboard);
            notifyDashboardReloaded(dashboard);
            logger.info("Imported dashboard " + dashboard.getId());
        } catch (DashboardImportException e) {
            logger.error("Failed to import dashboard.",e);
        }
    }

    private Dashboard convertToDashboard(File jsonFile) throws DashboardImportException {
        try {
                return mapper.readValue(jsonFile, Dashboard.class);

            } catch (IOException e) {
                throw new DashboardImportException(
                        "Could not parse dashboard file '" + jsonFile.getAbsolutePath() + "' as JSON.",e);
            }
    }

    private List<File> listJsonFiles() {
        File dashboardDir = findDashboardBaseDir();

        if(!dashboardDir.exists()){
            logger.warn("Dashboard config dir does not exist, no dashboards will be imported.");
            return Collections.emptyList();
        }

        return asList(dashboardDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(JSON);
            }
        }));
    }

    private File findDashboardBaseDir() {
        File rootDir = configRoot.getDirectory();
        File dashboardDir = new File(rootDir,"dashboards");
        return dashboardDir;
    }
}
