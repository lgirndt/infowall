package infowall.setup;

import infowall.domain.service.DashboardImporter;
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

    @Autowired
    public SetupBean(DashboardImporter dashboardImporter) {
        this.dashboardImporter = dashboardImporter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Setup Application.");

        dashboardImporter.importAllDashboards();
    }
}
