package infowall.domain.model;

import java.util.List;

/**
 *
 */
public class ConfigureDashboard {
    private final Dashboard dashboard;
    private final List<ConfigureItem> configureItems;

    public ConfigureDashboard(Dashboard dashboard, List<ConfigureItem> configureItems) {
        this.dashboard = dashboard;
        this.configureItems = configureItems;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public List<ConfigureItem> getConfigureItems() {
        return configureItems;
    }
}
