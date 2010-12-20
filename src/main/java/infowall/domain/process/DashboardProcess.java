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

package infowall.domain.process;

import com.google.common.collect.Lists;
import infowall.domain.model.*;
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
    private final ScriptFileProvider scriptFileProvider;

    @Autowired
    public DashboardProcess(
            DashboardRepository dashboardRepository,
            DashboardImporter dashboardImporter,
            ScriptFileProvider scriptFileProvider) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardImporter = dashboardImporter;
        this.scriptFileProvider = scriptFileProvider;
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

    public ConfigureDashboard getConfigureDashboard(String dashboardId){
        Dashboard dashboard = getDashboard(dashboardId);
        if(dashboard == null){
            return null;
        }
        List<ConfigureItem> configureItems = Lists.newArrayList();
        for(DashboardItem item : dashboard.getItems()){
            boolean scriptExists = scriptFileProvider.existsScriptFile(new DashboardItemRef(dashboardId,item.getName()));
            ConfigureItem configureItem = new ConfigureItem(item,scriptExists);
            configureItems.add(configureItem);
        }
        return new ConfigureDashboard(dashboard,configureItems);
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
