package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;
import org.joda.time.DateTime;

import java.util.List;

/**
 *
 */
public class SqlItemValueRepository implements ItemValueRepository {

    private final ItemValueDao dao;

    public SqlItemValueRepository(ItemValueDao dao) {
        this.dao = dao;
    }

    @Override
    public void put(ItemValue itemValue) {
        DashboardItemRef itemRef = itemValue.getItemRef();
        ItemValue existing = dao.findMostRecent(itemRef);

        if (existsWithEqualData(existing, itemValue)) {
            update(existing);
        } else {
            insertAsNew(itemValue);
        }
    }

    private void insertAsNew(ItemValue itemValue) {
        itemValue.init(new DateTime());
        dao.insert(itemValue);
    }

    private void update(ItemValue existing) {
        existing.update(new DateTime());
        dao.update(existing);
    }

    private boolean existsWithEqualData(ItemValue existing, ItemValue itemValue) {
        return existing != null && existing.equalData(itemValue);
    }

    @Override
    public List<ItemValue> findMostRecentItemValues(DashboardItemRef itemRef,int itemCount) {
        return dao.findMostRecentItemValues(itemRef,itemCount);
    }
}
