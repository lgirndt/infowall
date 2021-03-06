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

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 */
public class Dashboard  {

    private String id;

    private String title;

    private List<DashboardItem> items;

    private String animation;

    private int delay;

    private String theme = "";

    public Dashboard() {
    }

    public List<DashboardItem> getItems() {
        return items;
    }

    public void setItems(List<DashboardItem> items) {
        this.items = items;
    }

    public DashboardItem find(ItemRef itemRef){
        if(!itemRef.getDashboardId().equals(id)){
            return null;
        }
        for(DashboardItem item : this.items){
            if(itemRef.getItemName().equals(item.getName())){
                return item;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(final String animation) {
        this.animation = animation;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(final int delay) {
        this.delay = delay;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public String toJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "{}";
        }
    }
}
