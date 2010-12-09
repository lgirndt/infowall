package infowall.domain.model;

import org.codehaus.jackson.node.ObjectNode;

import java.util.Date;

/**
 *
 */
public class ItemValue extends TypedCouchDbDocument{

    private DashboardItemRef itemRef;

    private ObjectNode data;

    private Date creation;
    private Date lastUpdate;
    private long updateCount;

    public ItemValue() {
        super("itemValue");
    }

    public DashboardItemRef getItemRef() {
        return itemRef;
    }

    public void setItemRef(DashboardItemRef itemRef) {
        this.itemRef = itemRef;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }
}
