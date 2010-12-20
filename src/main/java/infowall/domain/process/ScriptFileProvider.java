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

import infowall.domain.model.DashboardItemRef;
import infowall.infrastructure.ConfigRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 *
 */
@Service
public class ScriptFileProvider {

    private final ConfigRoot configRoot;

    @Autowired
    public ScriptFileProvider(ConfigRoot configRoot) {
        this.configRoot = configRoot;
    }

    public File toScriptFile(DashboardItemRef itemRef) {
        File root = configRoot.getDirectory();
        File scriptDir = new File(root,"scripts");
        File dashboardDir = new File(scriptDir,itemRef.getDashboardId());
        return new File(dashboardDir,itemRef.getItemName() + ".groovy");
    }

    public boolean existsScriptFile(DashboardItemRef itemRef){
        File file = toScriptFile(itemRef);
        return file.exists();
    }
}
