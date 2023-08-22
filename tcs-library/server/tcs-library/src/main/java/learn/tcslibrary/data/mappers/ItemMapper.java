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
//        item.setTitle(rs.getString("title"));
        item.setInternetArchiveIdentifier(rs.getString("identifier"));
//        item.setDescription(rs.getString("description"));
//        item.setSubject(rs.getString("subject"));
        item.setFileName(rs.getString("filename"));
//        item.setImgLink(rs.getString("img_link"));
        return item;
    }
}
