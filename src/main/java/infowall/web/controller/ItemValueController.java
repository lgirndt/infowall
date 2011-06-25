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

import static infowall.web.model.ReturnStatus.failed;
import static infowall.web.model.ReturnStatus.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import infowall.domain.model.ItemRef;
import infowall.domain.model.ItemValuePair;
import infowall.domain.service.ItemValueService;
import infowall.web.model.ReturnStatus;

/**
 *
 */
@Controller
public class ItemValueController {

    private final ItemValueService itemValueProcess;

    @Autowired
    public ItemValueController(ItemValueService itemValueProcess) {
        this.itemValueProcess = itemValueProcess;
    }

    @RequestMapping(value="/single/item/{dashboardId}/{itemName}/{value}",method = RequestMethod.PUT)
    @ResponseBody
    public ReturnStatus storeSimpleValue(
            @PathVariable String dashboardId,
            @PathVariable String itemName,
            @PathVariable String value){

        itemValueProcess.storeSimpleValue(dashboardId,itemName, value);
        return success();
    }

    @RequestMapping(value="/item/{dashboardId}/{itemName}",method = RequestMethod.PUT)
    @ResponseBody
    public ReturnStatus storeValueWithBody(
            @PathVariable String dashboardId,
            @PathVariable String itemName,
            @RequestBody String value){

        return status(itemValueProcess.storeItemValue(dashboardId, itemName, value));
    }

    @RequestMapping(value="/item/{dashboardId}/{itemName}", method = RequestMethod.GET)
    @ResponseBody
    public ItemValuePair getValueDate(@PathVariable String dashboardId,@PathVariable String itemName){
        return itemValueProcess.showRecentValues(new ItemRef(dashboardId,itemName));
    }

    private ReturnStatus status(boolean status) {
        if(status){
            return success();
        }
        else {
            return failed();
        }
    }
}
