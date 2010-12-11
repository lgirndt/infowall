package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 */
@ContextConfiguration(locations = "classpath:/spring/test-context.xml")
public class ItemValueDaoTest extends AbstractTransactionalJUnit4SpringContextTests
/*AbstractJUnit4SpringContextTests*/ {

    @Resource
    private ItemValueDao itemValueDao;
    
    @Before
    public void setUp() throws Exception {
        this.executeSqlScript("/sql/create-schema.sql",false);
    }

    @Test
    public void insert(){

        DashboardItemRef itemRef = new DashboardItemRef("d","i");

        ItemValue itemValue = new ItemValue();

        itemValue.setItemRef(itemRef);
        itemValue.setCreation(new DateTime());
        itemValue.setLastUpdate(new DateTime());

        itemValueDao.insert(itemValue);        
    }

    @Test
    public void findExisting() throws Exception {
        DashboardItemRef itemRef = new DashboardItemRef("d", "i");
        String data = "{\"foo\":\"bar\"}";

        ItemValue val = itemValue(itemRef, data);

        itemValueDao.insert(val);
        ItemValue actual = itemValueDao.find(itemRef);

        assertNotNull(actual.getId());
    }

    @Test
    public void findNotExisting() throws Exception {
        ItemValue val = someItemValue();

        itemValueDao.insert(val);
        assertNull( itemValueDao.find(new DashboardItemRef("a","b")) );
    }

    @Test
    public void findMostRecent() throws Exception{
        DashboardItemRef itemRef = new DashboardItemRef("d", "i");
        String data = "{\"foo\":\"bar\"}";

        ItemValue val = itemValue(itemRef, data);

        itemValueDao.insert(val);
        ItemValue actual = itemValueDao.findMostRecent(itemRef);

        assertNotNull(actual.getId());
    }

    @Test
    public void findMostRecentItemValues() throws Exception {
        ItemValue first = someItemValue();
        ItemValue second = someItemValue();
        ItemValue third = someItemValue();

        itemValueDao.insert(first);
        itemValueDao.insert(second);
        itemValueDao.insert(third);

        List<ItemValue> result = itemValueDao.findMostRecentItemValues(first.getItemRef(),2);
        assertThat(result.size(), is(2));
    }

    private ItemValue someItemValue() throws IOException {
        DashboardItemRef itemRef = new DashboardItemRef("d", "i");
        String data = "{\"foo\":\"bar\"}";

        return itemValue(itemRef, data);
    }

    @Test
    public void update() throws Exception{
        ItemValue itemValue = someItemValue();
        itemValueDao.insert(itemValue);
        ItemValue withId = itemValueDao.find(itemValue.getItemRef());
        withId.update(new DateTime());
        itemValueDao.update(withId);
    }

    private ItemValue itemValue(DashboardItemRef itemRef, String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ItemValue val = new ItemValue();

        val.setItemRef(itemRef);
        val.setCreation(new DateTime());
        val.setUpdateCount(0);
        val.setLastUpdate(new DateTime());

        val.setData(mapper.readValue(data, ObjectNode.class));
        return val;
    }
}
