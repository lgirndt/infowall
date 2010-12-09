package infowall.domain.model;

import java.util.List;

/**
 *
 */
public class Dashboard extends TypedCouchDbDocument {

    private static final String TYPE = "dashboard";

    private String title;

    private List<DashboardItem> items;

    public Dashboard() {
        super(TYPE);
    }

    public List<DashboardItem> getItems() {
        return items;
    }

    public void setItems(List<DashboardItem> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
