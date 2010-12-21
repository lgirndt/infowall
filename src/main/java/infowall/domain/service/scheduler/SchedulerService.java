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

package infowall.domain.service.scheduler;

import infowall.domain.model.Dashboard;
import infowall.domain.model.DashboardItem;
import infowall.domain.model.ItemRef;
import infowall.domain.persistence.DashboardRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static com.google.common.collect.ImmutableMap.of;

/**
 *
 */
@Service
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);


    private final DashboardRepository dashboardRepository;
    private final Scheduler scheduler;
    private final BeanFactory beanFactory;

    @Autowired
    public SchedulerService(
            DashboardRepository dashboardRepository,
            Scheduler scheduler,
            BeanFactory beanFactory) {
        this.dashboardRepository = dashboardRepository;
        this.scheduler = scheduler;
        this.beanFactory = beanFactory;
    }

    public void registerDashboardJobs(String dashboardId) {

        String group = dashboardId;
        Dashboard dashboard = dashboardRepository.get(dashboardId);

        logger.info("Register jobs of Dashboard: " + dashboardId);

        try {
            scheduler.pauseJobGroup(group);
            scheduler.pauseTriggerGroup(group);
            for (DashboardItem item : dashboard.getItems()) {
                if (item.getScheduler() != null) {
                    registerJobForItem(group, item);
                }
            }
            scheduler.resumeJobGroup(group);
            scheduler.resumeTriggerGroup(group);

        } catch (SchedulerException e) {
            logger.error("Cannot register jobs for dashboard " + dashboardId, e);
        } catch (ParseException e) {
            logger.error("Cannot register jobs for dashboard " + dashboardId, e);
        }
    }

    private void registerJobForItem(String group, DashboardItem item) throws SchedulerException, ParseException {
        logger.info("Setup Job for DashboardItem " + item.getName());

        String jobName = item.getName();
        String triggerName = jobName + "-trigger";
        scheduler.unscheduleJob(triggerName,group);

        JobDetail job = new JobDetail(jobName, group, ScriptExecutingJob.class);
        JobDataMap map = new JobDataMap(
                of("beanFactory", beanFactory,
                        "itemRef", new ItemRef(group,item.getName())
                ));

        job.setJobDataMap(map);
        scheduler.addJob(job, true);



        CronTrigger cronTrigger = new CronTrigger(triggerName, group, jobName, group, item.getScheduler());
        scheduler.scheduleJob(cronTrigger);
    }

    public void registerAllDashboardJobs() {
        for (Dashboard dashboard : dashboardRepository.getAll()) {
            registerDashboardJobs(dashboard.getId());
        }
    }
}
