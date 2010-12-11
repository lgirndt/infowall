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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DashboardItemRef that = (DashboardItemRef) o;

        if (dashboardId != null ? !dashboardId.equals(that.dashboardId) : that.dashboardId != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dashboardId != null ? dashboardId.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        return result;
    }
}
