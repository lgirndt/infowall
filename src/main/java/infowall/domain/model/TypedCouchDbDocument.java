package infowall.domain.model;

import org.ektorp.support.CouchDbDocument;

/**
 *
 */
public abstract class TypedCouchDbDocument extends CouchDbDocument {

    private String type;

    protected TypedCouchDbDocument(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
