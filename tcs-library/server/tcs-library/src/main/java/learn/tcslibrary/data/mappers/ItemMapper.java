package learn.tcslibrary.data.mappers;

import learn.tcslibrary.models.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import learn.tcslibrary.data.ItemJdbcTemplateRepository;

public class ItemMapper implements RowMapper<Item> {
    private JdbcTemplate jdbcTemplate;

    public ItemMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setTitle(rs.getString("title"));
        item.setAuthor(rs.getString("author"));
//        item.setPublishedDate(rs.getString("published"));
//        item.setPublisher(rs.getString("publisher"));
        List<String> topics = findTopicsByItemId(item.getItemId());
        item.setTopic(topics);
//        item.setPageAmount(rs.getInt("pages"));
//        item.setLanguage(rs.getString("language"));
        item.setInternetArchiveIdentifier(rs.getString("ia_id"));
        return item;
    }

    public List<String> findTopicsByItemId(int itemId){
        final String sql = "select topic from item where item_id= ? ;";
        return jdbcTemplate.queryForList(sql, String.class, itemId);
    }


}
