package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;

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
}
