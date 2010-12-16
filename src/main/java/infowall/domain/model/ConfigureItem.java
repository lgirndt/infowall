package infowall.domain.model;

/**
 *
 */
public class ConfigureItem {
    private DashboardItem item;
    /** does a script exist?*/
    private boolean executable;

    public ConfigureItem(DashboardItem item, boolean executable) {
        this.item = item;
        this.executable = executable;
    }

    public DashboardItem getItem() {
        return item;
    }

    public boolean isExecutable() {
        return executable;
    }
}
