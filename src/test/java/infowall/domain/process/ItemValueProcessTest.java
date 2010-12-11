package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.model.ItemValuePair;
import infowall.domain.persistence.ItemValueRepository;
import infowall.testing.Mocks;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class ItemValueProcessTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testShowRecentValues() throws Exception {
        Mocks mocks = new Mocks();

        ItemValueRepository repository = mocks.createMock(ItemValueRepository.class);
        ItemValueProcess process = new ItemValueProcess(repository);

        ItemValue curr = mocks.createMock(ItemValue.class);
        ItemValue prev = mocks.createMock(ItemValue.class);
        expect(repository.findMostRecentItemValues(eq(itemRef()),eq(2))).andReturn(newArrayList(curr,prev));

        mocks.replayAll();
        ItemValuePair pair = process.showRecentValues(itemRef());
        assertThat(pair.getCurrent(), is(curr));
        assertThat(pair.getPrevious(), is(prev));
        mocks.verifyAll();
    }

    private DashboardItemRef itemRef() {
        return new DashboardItemRef("dashboardId","itemName");
    }
}
