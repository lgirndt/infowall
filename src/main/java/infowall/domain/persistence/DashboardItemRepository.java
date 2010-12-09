package infowall.domain.persistence;

import infowall.domain.model.DashboardItem;

/**
 *
 */
public interface DashboardItemRepository {

    DashboardItem get(String id);
    
    void add(DashboardItem item);
}
