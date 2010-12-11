package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 *
 */
public class ItemValueDao {

    private final JdbcTemplate jdbcTemplate;

    public ItemValueDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ItemValue itemValue) {

    }

    public ItemValue find(DashboardItemRef itemRef) {
        return null;
    }

    public void update(ItemValue itemValue) {

    }

    ItemValue findMostRecentItemValue(DashboardItemRef itemRef){
        return null;
    }
}
