package infowall.domain.model;

public class EditItemValue {
    private final ItemRef itemRef;
    private final String dashboardTitle;
    private final String itemTitle;

    private final String data;

    public EditItemValue(final ItemRef itemRef, final String dashboardTitle, final String itemTitle, final String data) {
        this.itemRef = itemRef;
        this.dashboardTitle = dashboardTitle;
        this.itemTitle = itemTitle;
        this.data = data;
    }

    public ItemRef getItemRef() {
        return itemRef;
    }

    public String getDashboardTitle() {
        return dashboardTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getData() {
        return data;
    }
}
