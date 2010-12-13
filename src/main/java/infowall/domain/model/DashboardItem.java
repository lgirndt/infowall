package infowall.domain.model;

import org.codehaus.jackson.node.ObjectNode;

/**
 *
 */
public class DashboardItem {

    private String name;
    private String title;
    private int statusThreshold;
    private ObjectNode conf;

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

    public ObjectNode getConf() {
        return conf;
    }

    public void setConf(ObjectNode conf) {
        this.conf = conf;
    }
}
