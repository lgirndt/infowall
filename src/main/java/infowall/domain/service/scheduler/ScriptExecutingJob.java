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

import infowall.domain.model.ItemRef;
import infowall.domain.model.ItemRef;
import infowall.domain.process.ScriptExecutorProcess;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 *
 */
public class ScriptExecutingJob implements StatefulJob {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutingJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job " + context.getJobDetail().getFullName() + " executed");

        JobDataMap map = context.getMergedJobDataMap();
        BeanFactory beanFactory = (BeanFactory) map.get("beanFactory");
        ItemRef itemRef = (ItemRef) map.get("itemRef");

        ScriptExecutorProcess scriptExecutorProcess = beanFactory.getBean(ScriptExecutorProcess.class);
        scriptExecutorProcess.execScriptAndStoreOutput(itemRef);
    }
}
