package infowall.domain.service;

import infowall.domain.model.Dashboard;

/**
 *
 */
public interface DashboardImportListener {

    public void onDashboardReloaded(Dashboard dashboard);
}
