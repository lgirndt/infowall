package infowall.web.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import infowall.domain.model.Dashboard;
import infowall.domain.process.DashboardProcess;
import infowall.web.services.errorhandling.Errors;
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
import java.util.Map;

import static infowall.web.spring.ControllerDsl.*;

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
            return null;
        }
        return render("dashboard/configure","dashboard",dashboard);
    }

    @RequestMapping("/reload/dashboard/{dashboardId}")
    ModelAndView reloadDashboard(@PathVariable String dashboardId){
        Errors errors = new Errors();
        Dashboard dashboard = process.reloadDashboard(dashboardId,errors);
        if(dashboard == null){
            return to404();
        }
        Map<String,Object> model =
                Maps.newHashMap(
                    ImmutableMap.<String, Object>of("dashboard", dashboard)
                );
        if(!errors.haveOccurred()){
            model.put("info","The configuration has been reloaded.");
        }
        return render("dashboard/configure",errors, model);
    }


}
