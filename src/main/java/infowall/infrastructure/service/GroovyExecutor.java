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

package infowall.infrastructure.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;

/**
 *
 */
@Service
public class GroovyExecutor {

    private final Logger logger = LoggerFactory.getLogger(GroovyExecutor.class);

    public String exec(File file){
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream wrappedOut = new PrintStream(out);

            Binding binding = new Binding();
            binding.setProperty("out",wrappedOut);

            GroovyShell shell = new GroovyShell(binding);
            shell.run(file, Collections.emptyList());

            return out.toString("UTF-8");

        } catch (IOException e) {
            logger.error("Cannot exec groovy script " + file.getAbsolutePath(),e);
        }
        return null;
    }
}
