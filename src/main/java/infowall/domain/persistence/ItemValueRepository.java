package infowall.domain.persistence;

import infowall.domain.model.ItemValue;
import org.ektorp.support.GenericRepository;

/**
 *
 */
public interface ItemValueRepository extends GenericRepository<ItemValue> {

    ItemValue findLatesItemValue(String dashboardId,String itemName);
    public String findMostRecentItemValueId(String dashboardId, String itemName);
}
