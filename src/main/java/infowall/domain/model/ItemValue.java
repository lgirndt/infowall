package infowall.domain.model;

import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;

/**
 *
 */
public class ItemValue {

    private Long id;

    private DashboardItemRef itemRef;

    private ObjectNode data;

    private DateTime creation;
    private DateTime lastUpdate;
    private long updateCount;

    public ItemValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DashboardItemRef getItemRef() {
        return itemRef;
    }

    public void setItemRef(DashboardItemRef itemRef) {
        this.itemRef = itemRef;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(DateTime lastUpdate) {
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

    public boolean equalData(ItemValue that){
        return this.data.equals(that.data);
    }

    public void update(DateTime now){
        updateCount++;
        lastUpdate = now;
    }

    public void init(DateTime now){
        updateCount = 0;
        lastUpdate = now;
        creation = now;
    }
}
