package infowall.domain.persistence;

import infowall.domain.model.ItemValue;

/**
 *
 */
public interface ItemValueRepository {

    public ItemValue findMostRecentItemValue(String dashboardId, String itemName);

    ItemValue get(String id);

    void put(ItemValue itemValue);
}
