package infowall.web.controller;

import com.google.common.collect.ImmutableMap;
import infowall.domain.model.Dashboard;
import infowall.domain.process.DashboardProcess;
import infowall.web.services.errorhandling.Errors;
import infowall.web.spring.FlashMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static infowall.web.spring.ControllerDsl.*;

/**
 *
 */
@Controller
//@Scope("session")
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardProcess process;
    private final FlashMessage flashMessage;

    @Autowired
    public DashboardController(DashboardProcess process, FlashMessage flashMessage) {
        this.process = process;
        this.flashMessage = flashMessage;
    }

    @RequestMapping("/dashboard")
    ModelAndView listDashboards(){

        List<Dashboard> dashboards = process.listAllDashboards();

        return render(
                "dashboard/list",
                "dashboards", dashboards);
    }

    private String toJson(List<Dashboard> dashboards) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json= mapper.writeValueAsString(dashboards);
        } catch (IOException e) {
            json = "{}";
        }
        return json;
    }

    @RequestMapping("/dashboard/{dashboardId}")
    ModelAndView showDashboard(@PathVariable String dashboardId){
        Dashboard dashboard = process.getDashboard(dashboardId);
        if(dashboard == null){
            return redirect("/dashboard");
        }
        return render("dashboard/index",
                "dashboard",dashboard,
                "json",dashboard.toJSON());
    }

    @RequestMapping("/configure/dashboard/{dashboardId}")
    ModelAndView configureDashboard(@PathVariable String dashboardId){

        Dashboard dashboard = process.getDashboard(dashboardId);
        if(dashboard == null){
            return to404();
        }
        
        return render(
                "dashboard/configure",
                flashMessage,
                ImmutableMap.<String, Object>of("dashboard",dashboard));
    }

    @RequestMapping("/reload/dashboard/{dashboardId}")
    ModelAndView reloadDashboard(@PathVariable String dashboardId){
        Errors errors = new Errors();
        Dashboard dashboard = process.reloadDashboard(dashboardId,errors);
        if(dashboard == null){
            return to404();
        }

        if(!errors.haveOccurred()){
            flashMessage.putInfo("The configuration has been reloaded.");
        }
        return redirect("/configure/dashboard/" + dashboardId);
    }


}
