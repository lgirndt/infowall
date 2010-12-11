package infowall.domain.persistence.sql;

import com.google.common.collect.Lists;
import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.testing.Mocks;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class SqlItemValueRepositoryTest {
    private Mocks mocks;
    private ItemValueDao dao;
    private SqlItemValueRepository repository;

    @Before
    public void setUp() throws Exception {
        mocks = new Mocks();
        dao = mocks.createMock(ItemValueDao.class);
        repository = new SqlItemValueRepository(dao);
    }

    @Test
    public void putNew() throws Exception {

        DashboardItemRef itemRef = itemRef();
        expect(dao.findMostRecent(itemRef)).andReturn(null);

        ItemValue itemValue = mockItemValue();
        expectGetItemRef(itemValue, itemRef);
        expectInit(itemValue);

        dao.insert(itemValue);
        
        mocks.replayAll();
        repository.put(itemValue);

        mocks.verifyAll();
    }

    @Test
    public void putExistingWithDifferentData() {
        ItemValue existing = mockItemValue();
        ItemValue itemValue = mockItemValue();
        
        expectGetItemRef(itemValue, itemRef());
        expectInit(itemValue);

        expect(dao.findMostRecent(eq(itemRef()))).andReturn(existing);
        expectEqualData(existing, itemValue, false);
        dao.insert(itemValue);

        mocks.replayAll();
        repository.put(itemValue);

        mocks.verifyAll();
    }

    @Test
    public void putExistingWithSameData(){
        ItemValue existing = mockItemValue();
        ItemValue itemValue = mockItemValue();

        expectGetItemRef(itemValue, itemRef());
        existing.update((DateTime) anyObject());

        expect(dao.findMostRecent(eq(itemRef()))).andReturn(existing);
        expectEqualData(existing,itemValue,true);
        dao.update(existing);

        mocks.replayAll();
        repository.put(itemValue);
        mocks.verifyAll();
    }

    @Test
    public void findMostRecentItemValuesExisting(){
        ItemValue expectedItem = mockItemValue();

        List<ItemValue> expected = Lists.<ItemValue>newArrayList(expectedItem);
        expect(dao.findMostRecentItemValues(eq(itemRef()),eq(2))).andReturn(expected);

        mocks.replayAll();
        List<ItemValue> actual = repository.findMostRecentItemValues(itemRef(),2);
        assertThat(actual, is(expected));
        mocks.verifyAll();
    }

    private void expectEqualData(ItemValue existing, ItemValue itemValue, boolean same) {
        expect(existing.equalData(itemValue)).andReturn(same);
    }

    private ItemValue mockItemValue() {
        return mocks.createMock(ItemValue.class);
    }

    private void expectGetItemRef(ItemValue itemValue, DashboardItemRef itemRef) {
        expect(itemValue.getItemRef()).andReturn(itemRef);
    }

    private void expectInit(ItemValue itemValue) {
        // TODO use Joda's test facility.
        itemValue.init((DateTime) anyObject());
    }

    private DashboardItemRef itemRef() {
        return new DashboardItemRef("d", "i");
    }
}
