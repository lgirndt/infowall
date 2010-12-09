package infowall.domain.model;

/**
 *
 */
public class DashboardItemRef {

    private String dashboardId;
    private String itemName;

    public DashboardItemRef(String dashboardId, String itemName) {
        this.dashboardId = dashboardId;
        this.itemName = itemName;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
