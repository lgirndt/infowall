package infowall.domain.model;

import org.codehaus.jackson.node.ObjectNode;
import org.ektorp.support.CouchDbDocument;

/**
 *
 */
public class DashboardItem extends CouchDbDocument {

    private ObjectNode data;

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }
}
