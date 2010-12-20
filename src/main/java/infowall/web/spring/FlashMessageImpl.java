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

package infowall.web.spring;

import infowall.web.services.errorhandling.Errors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Scope("session")
public class FlashMessageImpl implements FlashMessage {

    private String info;
    private Errors errors;

    public FlashMessageImpl() {
    }

    @Override
    public void putInfo(String msg){
        info = msg;
    }

    @Override
    public void putErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String consumeInfo(){
        String msg = info;
        info = null;
        return msg;
    }

    @Override
    public Errors consumeErrors() {
        Errors e = this.errors;
        this.errors = null;
        return e;
    }
}
