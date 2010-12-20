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

package infowall.setup;

import infowall.domain.service.DashboardImporter;
import infowall.domain.service.scheduler.SchedulerDashboardImportListener;
import infowall.domain.service.scheduler.SchedulerService;
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
    private final SchedulerService schedulerService;

    @Autowired
    public SetupBean(DashboardImporter dashboardImporter,
                     SchedulerService schedulerService) {
        this.dashboardImporter = dashboardImporter;
        this.schedulerService = schedulerService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Setup Application.");

        dashboardImporter.addListener(new SchedulerDashboardImportListener(schedulerService));

        dashboardImporter.importAllDashboards();
    }
}
