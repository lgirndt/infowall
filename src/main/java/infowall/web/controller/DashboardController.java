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

import static infowall.web.spring.ControllerDsl.redirect;
import static infowall.web.spring.ControllerDsl.render;

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

    @RequestMapping("/dashboard")
    ModelAndView listDashboards(){
        return render("dashboard/list","dashboards", process.listAllDashboards());
    }

    @RequestMapping("/dashboard/{dashboardId}")
    ModelAndView showDashboard(@PathVariable String dashboardId){
        Dashboard dashboard = process.getDashboard(dashboardId);
        if(dashboard == null){
            return redirect("/dashboard");
        }
        return render("dashboard/index","dashboard",dashboard);
    }
}
