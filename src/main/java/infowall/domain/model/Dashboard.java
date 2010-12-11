package infowall.domain.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Dashboard  {

    private String id;

    private String title;

    private List<DashboardItem> items;

    public Dashboard() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "{}";
        }
    }
}
