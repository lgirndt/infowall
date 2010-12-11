package infowall.domain.persistence.sql;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
@ContextConfiguration(locations = "classpath:/spring/test-context.xml")
public class SqlItemValueRepositoryTest extends AbstractJUnit4SpringContextTests{

    @Resource
    private SqlItemValueRepository itemValueRepository;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPut() throws Exception {
        assertNotNull(itemValueRepository);
    }

    @Test
    public void testFindMostRecentItemValue() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
    }
}
