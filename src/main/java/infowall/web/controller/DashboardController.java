package infowall.web.controller;

import infowall.domain.model.Dashboard;
import infowall.domain.process.DashboardProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 */
@Controller
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardProcess process;

    @Autowired
    public DashboardController(DashboardProcess process) {
        this.process = process;
    }

    @RequestMapping("/dashboards")
    ModelAndView listDashboards(){
        List<Dashboard> dashboards = process.listAllDashboards();

        logger.info("loaded dashboards: " + dashboards);

        return new ModelAndView("dashboard/list","dashboards", dashboards);
    }

    @RequestMapping("/dashboard/{dashboardId}")
    ModelAndView showDashboard(@PathVariable String dashboardId){
        Dashboard dashboard = process.getDashboard(dashboardId);
        if(dashboard == null){
            return new ModelAndView("redirect:/action/dashboards");
        }
        return new ModelAndView("dashboard/index","dashboard",dashboard);
    }
}
