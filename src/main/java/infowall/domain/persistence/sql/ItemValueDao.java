package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;

/**
 *
 */
public class ItemValueDao {

    private final JdbcTemplate jdbcTemplate;
    private ObjectMapper mapper;

    public ItemValueDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        mapper = new ObjectMapper();
    }

    public void insert(ItemValue itemValue) {
        try {
            String data = serializeData(itemValue);

            jdbcTemplate.update(
                "INSERT INTO item_value " +
                "(creation,dashboard_id,data,item_name,last_update,update_count)" +
                " VALUES (?,?,?,?,?,?)",

                toTimestamp(itemValue.getCreation()),
                itemValue.getItemRef().getDashboardId(),
                data,
                itemValue.getItemRef().getItemName(),
                toTimestamp(itemValue.getLastUpdate()),
                itemValue.getUpdateCount());
            
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    private String serializeData(ItemValue itemValue) throws IOException {
        return mapper.writeValueAsString(itemValue.getData());
    }

    private Timestamp toTimestamp(DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
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
