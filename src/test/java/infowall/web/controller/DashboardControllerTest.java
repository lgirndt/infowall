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

import com.google.common.collect.Lists;
import infowall.domain.model.Dashboard;
import infowall.domain.process.DashboardProcess;
import infowall.domain.process.ScriptExecutorProcess;
import infowall.testing.Mocks;
import infowall.web.spring.FlashMessage;
import infowall.web.spring.FlashMessageImpl;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 *
 */
public class DashboardControllerTest {
    private Mocks mocks;
    private DashboardProcess process;
    private DashboardController controller;

    @Before
    public void setUp() throws Exception {
        mocks = new Mocks();
        process = mocks.createMock(DashboardProcess.class);
        FlashMessage flash = mocks.createMock(FlashMessageImpl.class);
        ScriptExecutorProcess scriptExecutorProcess = mocks.createMock(ScriptExecutorProcess.class);
        controller = new DashboardController(process, flash, scriptExecutorProcess);
    }

    @Test
    public void listAllDashboards(){

        List<Dashboard> actual = Lists.newArrayList();

        EasyMock.expect(process.listAllDashboards()).andReturn(actual);

        mocks.replayAll();
        ModelAndView mav = controller.listDashboards();
        mocks.verifyAll();

        assertViewName(mav,"dashboard/list");
        assertModelAttributeValue(mav,"dashboards",actual);
    }
}
