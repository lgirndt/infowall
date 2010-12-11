package infowall.domain.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import infowall.infrastructure.ConfigRoot;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 *
 */
@Service
public class DashboardImporter {

    private final Logger logger = LoggerFactory.getLogger(DashboardImporter.class);

    private final ConfigRoot configRoot;
    private final DashboardRepository dashboardRepository;

    @Autowired
    public DashboardImporter(
            ConfigRoot configRoot,
            DashboardRepository dashboardRepository) {

        this.configRoot = configRoot;
        this.dashboardRepository = dashboardRepository;
    }

    public void importAllDashboards() {
        List<File> files = listJsonFiles();
        List<Dashboard> dashboards = convertToDashboards(files);
        storeDashboards(dashboards);

        logStatus(dashboards);
    }

    private void logStatus(List<Dashboard> dashboards) {
        List<String> ids = Lists.transform(dashboards,new Function<Dashboard, String>() {
            @Override
            public String apply(Dashboard dashboard) {
                return dashboard.getId();
            }
        });

        logger.info("Dashboards imported: {}",ids);
    }

    private void storeDashboards(List<Dashboard> dashboards) {
        for(Dashboard dashboard : dashboards){

            dashboardRepository.put(dashboard);
        }
    }

    private List<Dashboard> convertToDashboards(List<File> files) {
        List<Dashboard> dashboards = Lists.newArrayList();
        ObjectMapper mapper = new ObjectMapper();

        for(File jsonFile : files){
            try {
                Dashboard dashboard = mapper.readValue(jsonFile, Dashboard.class);
                dashboards.add(dashboard);

            } catch (IOException e) {
                logger.error("Could not parse dashboard file '" + jsonFile.getAbsolutePath() + "' as JSON.",e);
            }
        }
        return dashboards;
    }

    private List<File> listJsonFiles() {
        File rootDir = configRoot.getDirectory();
        File dashboardDir = new File(rootDir,"dashboards");

        if(!dashboardDir.exists()){
            logger.warn("Dashboard config dir does not exist, no dashboards will be imported.");
            return Collections.emptyList();
        }

        return asList(dashboardDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        }));
    }
}
