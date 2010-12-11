package infowall.domain.persistence.sql;

import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 *
 */
public class SqlItemValueRepository implements ItemValueRepository{

    private final JdbcTemplate jdbcTemplate;

    public SqlItemValueRepository(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void put(ItemValue entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ItemValue findMostRecentItemValue(String dashboardId, String itemName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ItemValue get(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
