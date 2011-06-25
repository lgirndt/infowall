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

package infowall.domain.service.script;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import infowall.domain.model.ItemRef;
import infowall.domain.service.ItemValueService;
import infowall.infrastructure.service.GroovyExecutor;

/**
 *
 */
@Service
public class ScriptExecutorService {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutorService.class);

    private final GroovyExecutor groovyExecutor;
    private final ItemValueService itemValueProcess;
    private final ScriptFileProvider scriptFileProvider;

    @Autowired
    public ScriptExecutorService(
            GroovyExecutor groovyExecutor,
            ItemValueService itemValueProcess,
            ScriptFileProvider scriptFileProvider) {
        this.groovyExecutor = groovyExecutor;
        this.itemValueProcess = itemValueProcess;
        this.scriptFileProvider = scriptFileProvider;
    }

    public String printScriptOutput(ItemRef itemRef){
        String content = execScript(itemRef);
        return content;
        /*
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,ObjectNode.class);
        } catch (IOException e) {
            logger.error("cannot exec script",e);
            return null;
        }
        */
    }

    private String execScript(ItemRef itemRef) {
        File itemFile = scriptFileProvider.toScriptFile(itemRef);
        return groovyExecutor.exec(itemFile);
    }

    public void execScriptAndStoreOutput(ItemRef itemRef){
        String content = execScript(itemRef);
        if(content != null){
            itemValueProcess.storeItemValue(itemRef,content);
        }else {
            logger.warn("Could not store script result of " + itemRef + ", content was null.");
        }
    }
}
