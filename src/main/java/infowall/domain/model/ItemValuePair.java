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

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 *
 */
public class ItemValuePair {
    private final ItemValue current;
    private final ItemValue previous;

    public ItemValuePair(ItemValue current, ItemValue previous) {
        this.current = current;
        this.previous = previous;
    }

    public ItemValue getCurrent() {
        return current;
    }

    public ItemValue getPrevious() {
        return previous;
    }

    String toJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "{}";
        }
    }
}
