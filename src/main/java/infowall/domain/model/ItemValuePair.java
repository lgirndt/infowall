package infowall.domain.model;

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
}
