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

package infowall.domain.model;

import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;

/**
 *
 */
public class ItemValue {

    private Long id;

    private DashboardItemRef itemRef;

    private ObjectNode data;

    private DateTime creation;
    private DateTime lastUpdate;
    private long updateCount;

    public ItemValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DashboardItemRef getItemRef() {
        return itemRef;
    }

    public void setItemRef(DashboardItemRef itemRef) {
        this.itemRef = itemRef;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(DateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }

    public boolean equalData(ItemValue that){
        return this.data.equals(that.data);
    }

    public void update(DateTime now){
        updateCount++;
        lastUpdate = now;
    }

    public void init(DateTime now){
        updateCount = 0;
        lastUpdate = now;
        creation = now;
    }
}
