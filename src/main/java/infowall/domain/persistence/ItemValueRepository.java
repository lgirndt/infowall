package infowall.domain.persistence;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;

/**
 *
 */
public interface ItemValueRepository {

    public ItemValue findMostRecentItemValue(DashboardItemRef itemRef);

    void put(ItemValue itemValue);
}
