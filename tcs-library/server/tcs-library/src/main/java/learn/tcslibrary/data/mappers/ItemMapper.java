package learn.tcslibrary.data.mappers;

import learn.tcslibrary.models.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemMapper implements RowMapper<Item> {
    private final List<String> topics;

    public ItemMapper(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item=new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setTitle(rs.getString("title"));
        item.setAuthor(rs.getString("author"));
        item.setPublishedDate(rs.getString("published"));
        item.setPublisher(rs.getString("publisher"));
        item.setTopic(topics);
        item.setPageAmount(rs.getInt("pages"));
        item.setLanguage(rs.getString("language"));
        item.setInternetArchiveIdentifier(rs.getString("ia_id"));
        return item;

    }
}
