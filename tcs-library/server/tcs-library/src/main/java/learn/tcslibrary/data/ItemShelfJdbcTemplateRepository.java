package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.ItemMapper;
import learn.tcslibrary.data.mappers.ItemShelfMapper;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.jdbc.core.JdbcTemplate;
import learn.tcslibrary.data.mappers.ItemMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ItemShelfJdbcTemplateRepository implements ItemShelfRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemShelfJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ItemShelf> findByAppUserId(int appUserId) {
        final String sql = " select i.item_id, shelf.app_user_id " +
                "from item i inner join item_shelf shelf on i.item_id = shelf.item_id " +
                "where shelf.app_user_id = ?;";

        List<ItemShelf> itemShelfList = jdbcTemplate.query(sql, new ItemShelfMapper(), appUserId);
        return itemShelfList;
    }

    @Override
    public ItemShelf addItemToShelf(int itemId, int appUserId) {

        final String sql = "insert into item_shelf (page_number, item_id, app_user_id) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 1);
            ps.setInt(2, itemId);
            ps.setInt(3, appUserId);
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        int itemShelfId = keyHolder.getKey().intValue();
        ItemShelf itemShelf = new ItemShelf();
        itemShelf.setItemShelfId(itemShelfId);
        itemShelf.setPageNumber(1);
        itemShelf.setItemId(itemId);
        itemShelf.setAppUserId(appUserId);

        return (itemShelf);
    }

    @Override
    public boolean updatePageNumber(int pageNumber, int itemId, int appUserId) {
        final String sql = "update item_shelf set page_number = ? " +
                "where item_id = ? AND app_user_id = ?;";

        int rowsAffected = jdbcTemplate.update(sql, pageNumber, itemId, appUserId);



        return (rowsAffected > 0 ? true : false);
    }


    @Override
    public boolean deleteItemFromShelf(int itemId, int appUserId) {
        final String sql = "DELETE FROM item_shelf WHERE item_id = ? and app_user_id = ?";

        int isDeleted = jdbcTemplate.update(sql, itemId,appUserId);

        return isDeleted > 0;
    }

    //Make a find by APp user for just personal bookshelf

    public ItemShelf findByAppUserIdAndItemId(int appUserId, int itemId){
        final String sql = "select i.item_id, shelf.page_number, shelf.app_user_id, shelf.item_shelf_id " +
                "from item i inner join item_shelf shelf on i.item_id = shelf.item_id " +
                " where shelf.app_user_id = ? and i.item_id = ?; ";

        ItemShelf itemShelf = jdbcTemplate.queryForObject(sql, new ItemShelfMapper(), appUserId, itemId);



        return(itemShelf);
    }


}
