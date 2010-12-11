package infowall.domain.persistence.sql;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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

        jdbcTemplate.update(
                "INSERT INTO item_value " +
                        "(creation,dashboard_id,data,item_name,last_update,update_count)" +
                        " VALUES (?,?,?,?,?,?)",

                toTimestamp(itemValue.getCreation()),
                itemValue.getItemRef().getDashboardId(),
                serializeData(itemValue),
                itemValue.getItemRef().getItemName(),
                toTimestamp(itemValue.getLastUpdate()),
                itemValue.getUpdateCount());
    }

    public void update(ItemValue itemValue) {
        jdbcTemplate.update(
                "UPDATE item_value SET " +
                        "data = ?, last_update = ?, update_count = ? " +
                        "WHERE id = ?",
                serializeData(itemValue),
                toTimestamp(itemValue.getLastUpdate()),
                itemValue.getUpdateCount(),
                itemValue.getId()
        );
    }

    public ItemValue find(DashboardItemRef itemRef) {
        List<ItemValue> itemVal = jdbcTemplate.query(
                "SELECT id,creation,dashboard_id,item_name,data,last_update,update_count " +
                        "FROM item_value " +
                        "WHERE dashboard_id = ? and item_name = ?",
                new ItemValueRowMapper(),
                itemRef.getDashboardId(), itemRef.getItemName());
        if (itemVal.size() == 1) {
            return itemVal.get(0);
        }
        return null;
    }

    private String serializeData(ItemValue itemValue) {
        try {
            return mapper.writeValueAsString(itemValue.getData());
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    private ObjectNode deserializeData(String str) {
        try {
            return mapper.readValue(str, ObjectNode.class);
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    private static Timestamp toTimestamp(DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
    }

    private static DateTime toDateTime(Timestamp ts) {
        return new DateTime(ts.getTime());
    }

    private class ItemValueRowMapper implements RowMapper<ItemValue> {

        @Override
        public ItemValue mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemValue val = new ItemValue();
            val.setId(rs.getLong(1));
            val.setCreation(toDateTime(rs.getTimestamp(2)));

            DashboardItemRef itemRef = new DashboardItemRef(
                    rs.getString(3),
                    rs.getString(4)
            );
            val.setItemRef(itemRef);

            val.setData(deserializeData(rs.getString(5)));
            val.setLastUpdate(toDateTime(rs.getTimestamp(6)));
            val.setUpdateCount(rs.getInt(7));

            return val;
        }
    }
}
