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

package infowall.domain.persistence.sql;

import java.util.List;

import org.joda.time.DateTime;

import infowall.domain.model.ItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;

/**
 *
 */
public class SqlItemValueRepository implements ItemValueRepository {

    private final ItemValueDao dao;

    public SqlItemValueRepository(ItemValueDao dao) {
        this.dao = dao;
    }

    @Override
    public void put(ItemValue itemValue) {
        ItemRef itemRef = itemValue.getItemRef();
        ItemValue existing = dao.findMostRecent(itemRef);

        if (existsWithEqualData(existing, itemValue)) {
            update(existing);
        } else {
            insertAsNew(itemValue);
        }
    }

    private void insertAsNew(ItemValue itemValue) {
        itemValue.init(new DateTime());
        dao.insert(itemValue);
    }

    private void update(ItemValue existing) {
        existing.update(new DateTime());
        dao.update(existing);
    }

    private boolean existsWithEqualData(ItemValue existing, ItemValue itemValue) {
        return existing != null && existing.equalData(itemValue);
    }

    @Override
    public List<ItemValue> findMostRecentItemValues(ItemRef itemRef,int itemCount) {
        return dao.findMostRecentItemValues(itemRef,itemCount);
    }

    @Override
    public ItemValue findMostRecentItemValue(final ItemRef itemRef) {
        return dao.findMostRecent(itemRef);
    }
}
