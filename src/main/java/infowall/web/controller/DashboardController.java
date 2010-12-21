/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package infowall.web.controller;

import infowall.domain.model.ConfigureDashboard;
import infowall.domain.model.Dashboard;
import infowall.domain.model.ItemRef;
import infowall.domain.process.DashboardProcess;
import infowall.domain.process.ScriptExecutorProcess;
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
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardProcess process;
    private final FlashMessage flash;
    private final ScriptExecutorProcess scriptExecutorProcess;

    @Autowired
    public DashboardController(
            DashboardProcess process,
            FlashMessage flash,
            ScriptExecutorProcess scriptExecutorProcess) {
        this.process = process;
        this.flash = flash;
        this.scriptExecutorProcess = scriptExecutorProcess;
    }

    @RequestMapping("/dashboard")
    ModelAndView listDashboards(){

        List<Dashboard> dashboards = process.listAllDashboards();

        return render("dashboard/list","dashboards", dashboards);
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

        ConfigureDashboard configureDashboard = process.getConfigureDashboard(dashboardId);
        if(configureDashboard == null){
            return to404();
        }
        
        return render("dashboard/configure", flash,
                "dashboard", configureDashboard.getDashboard(),
                "items",configureDashboard.getConfigureItems());
    }

    @RequestMapping("/reload/dashboard/{dashboardId}")
    ModelAndView reloadDashboard(@PathVariable String dashboardId){
        Errors errors = new Errors();
        Dashboard dashboard = process.reloadDashboard(dashboardId,errors);
        if(dashboard == null){
            return to404();
        }

        if(!errors.haveOccurred()){
            flash.putInfo("The configuration has been reloaded.");
        }else {
            flash.putErrors(errors);
        }
        return redirect("/configure/dashboard/" + dashboardId);
    }

    @RequestMapping("/configure/exec/dashboard/{dashboardId}/{itemName}")
    ModelAndView executeScript(@PathVariable String dashboardId,@PathVariable String itemName){
        scriptExecutorProcess.execScriptAndStoreOutput(new ItemRef(dashboardId,itemName));
        flash.putInfo("Script executed.");
        return redirect("/configure/dashboard/" + dashboardId);
    }
}
