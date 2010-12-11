package infowall.domain.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 *
 */
public class ItemValuePair {
    private final ItemValue current;
    private final ItemValue previous;

    public ItemValuePair(ItemValue current, ItemValue previous) {
        this.current = current;
        this.previous = previous;
    }

    public ItemValue getCurrent() {
        return current;
    }

    public ItemValue getPrevious() {
        return previous;
    }

    String toJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "{}";
        }
    }
}
