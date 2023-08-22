package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.ItemMapper;
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
    public Item findByitemId(int itemId) {
        final String sql = " select i.item_id, i.identifier " +
                "from item i inner join item_shelf shelf on i.item_id = shelf.item_id where shelf.item_id = ?;";

        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate), itemId);
        return (itemList == null || itemList.isEmpty()) ? null : itemList.get(0);
    }

    // for sorting purposes
    // will need an order by in the sql?
    // will likely need to do the calculation in this repo (last page read divided by page amount (from item pagecount))
//    @Override
//    public List<Item> findByIncomplete() {
//        final String sql = "SELECT item_id, title, author, published, publisher, topic, pages, `language`, ia_id " +
//                "FROM item " +
//                "WHERE title IS NULL OR title = '' " +
//                "   OR author IS NULL OR author = ''" +
//                "   OR topic IS NULL OR topic = ''" +
//                "   OR `language` IS NULL OR `language` = ''" +
//                "   OR ia_id IS NULL OR ia_id = '';";
//       List<Item> items= jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate));
//       return(items==null||items.size()==0 ? null : items);
//    }

    @Override
    public Item addItemToShelf(int itemId, int appUserId) {
        Item item = findByitemId(itemId);

        final String sql = "insert into item_shelf (item_id, app_user_id) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, itemId, appUserId);

        return (rowsAffected > 0 ? item : null);
    }

    @Override
    public boolean updatePageNumber(int pageNumber, int itemId) {
        final String sql = "update item_shelf set page_number = ? " +
                "where item_id = ?;";

        int rowsAffected = jdbcTemplate.update(sql, itemId, pageNumber);

        return (rowsAffected > 0 ? true : false);
    }


    @Override
    public boolean deleteItemFromShelf(int itemId, int appUserId) {
        final String sql = "DELETE FROM item_shelf WHERE item_id = ? and app_user_id = ?";

        int isDeleted = jdbcTemplate.update(sql, itemId,appUserId);

        return isDeleted >0;
    }

    public List<Item> findByAppUserId(int appUserId){
        final String sql = "select i.item_id, shelf.app_user_id " +
                "from item i inner join item_shelf shelf on i.item_id = shelf.item_id" +
                " where shelf.app_user_id = ? ; ";

        List<Item> items = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),appUserId);

        return(items == null || items.size() == 0 ? null : items);
    }


}
