package infowall.domain.persistence;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;

import java.util.List;

/**
 *
 */
public interface ItemValueRepository {

    List<ItemValue> findMostRecentItemValues(DashboardItemRef itemRef,int itemCount);

    void put(ItemValue itemValue);
}
