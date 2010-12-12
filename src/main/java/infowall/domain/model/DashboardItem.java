package infowall.domain.model;

/**
 *
 */
public class DashboardItem {

    private String name;
    private String title;
    private int statusThreshold;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatusThreshold() {
        return statusThreshold;
    }

    public void setStatusThreshold(int statusThreshold) {
        this.statusThreshold = statusThreshold;
    }
}
