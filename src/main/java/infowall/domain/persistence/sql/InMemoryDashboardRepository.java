package infowall.domain.persistence.sql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import org.ektorp.DocumentNotFoundException;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class InMemoryDashboardRepository implements DashboardRepository{

    private final Map<String,Dashboard> dashboards;

    public InMemoryDashboardRepository() {
        this.dashboards = Maps.newHashMap();
    }

    private void putDashboard(Dashboard dashboard) {
        dashboards.put(dashboard.getId(), dashboard);
    }

    @Override
    public void put(Dashboard entity) {
        putDashboard(entity);
    }

    @Override
    public Dashboard get(String id) {
        Dashboard dashboard = dashboards.get(id);
        // TODO
        if(dashboard == null){
            throw new DocumentNotFoundException("");
        }
        return dashboard;
    }

    @Override
    public List<Dashboard> getAll() {
        return Lists.newArrayList(dashboards.values());
    }
}
