package infowall.domain.persistence;

import infowall.domain.model.Dashboard;

import java.util.List;

/**
 *
 */
public interface DashboardRepository {

    Dashboard get(String id);
    
    void add(Dashboard dashboard);
    void update(Dashboard dashboard);

    List<Dashboard> getAll();
}
