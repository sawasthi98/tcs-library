package learn.tcslibrary.data.mappers;

import com.mysql.cj.result.Row;
import learn.tcslibrary.models.ItemShelf;
import org.apache.catalina.realm.JAASCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemShelfMapper implements RowMapper<ItemShelf> {

   // private JdbcTemplate jdbcTemplate;

   // public ItemShelfMapper(JdbcTemplate jdbcTemplate) {
   //     this.jdbcTemplate = jdbcTemplate;
    //}

    @Override
    public ItemShelf mapRow(ResultSet rs, int rowNum) throws SQLException {

        ItemShelf itemShelf = new ItemShelf();
        itemShelf.setItemShelfId(rs.getInt("item_shelf_id"));
        itemShelf.setPageNumber(rs.getInt("page_number"));
        itemShelf.setItemId(rs.getInt("item_id"));
        itemShelf.setAppUserId(rs.getInt("app_user_id"));

        return itemShelf;
    }
}
