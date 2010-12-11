package infowall.domain.persistence;

import infowall.domain.model.Dashboard;

import java.util.List;

/**
 *
 */
public interface DashboardRepository {

    List<Dashboard> getAll();

    Dashboard get(String dashboardId);

    void put(Dashboard dashboard);
}
