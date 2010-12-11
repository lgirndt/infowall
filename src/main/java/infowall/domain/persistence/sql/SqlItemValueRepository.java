package infowall.domain.persistence.sql;

import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;

import java.util.List;

/**
 *
 */
public class SqlItemValueRepository implements ItemValueRepository{
    
    @Override
    public ItemValue findLatesItemValue(String dashboardId, String itemName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String findMostRecentItemValueId(String dashboardId, String itemName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(ItemValue entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(ItemValue entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(ItemValue entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ItemValue get(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ItemValue get(String id, String rev) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ItemValue> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(String docId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
