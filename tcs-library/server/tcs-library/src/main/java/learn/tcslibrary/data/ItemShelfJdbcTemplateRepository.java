package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.ItemMapper;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.jdbc.core.JdbcTemplate;
import learn.tcslibrary.data.mappers.ItemMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class ItemShelfJdbcTemplateRepository implements ItemShelfRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemShelfJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item findByitemId(int itemId) {
        final String sql = "select page_number,item_id,app_user_id" +
                "from item_shelf where item_id= ? ;";
        //List<String>topics=findTopicsByItemId(itemId);
        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),itemId);
        return(itemList == null||itemList.size()==0 ? null : itemList.get(0));
    }

    // for sorting purposes
    // will need an order by in the sql?
    // will likely need to do the calculation in this repo (last page read divided by page amount (from item pagecount))
    @Override
    public List<Item> findByIncomplete() {
        final String sql = "SELECT item_id, title, author, published, publisher, topic, pages, `language`, ia_id " +
                "FROM item " +
                "WHERE title IS NULL OR title = '' " +
                "   OR author IS NULL OR author = ''" +
                "   OR published IS NULL OR published = ''" +
                "   OR publisher IS NULL OR publisher = ''" +
                "   OR topic IS NULL OR topic = ''" +
                "   OR pages IS NULL OR pages = ''" +
                "   OR `language` IS NULL OR `language` = ''" +
                "   OR ia_id IS NULL OR ia_id = '';";
       List<Item> items= jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate));
       return(items==null||items.size()==0 ? null : items);
    }

    @Override
    public Item addItemToShelf(int itemId,int appUserId) {
        //get an item and assign it to the shelf of an app user
        Item item = findByitemId(itemId);
        final String sql = "insert into item_shelf values (page_number, item_id, app_user_id)" +
                "values (?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, item.getPageAmount());
            ps.setInt(2, itemId);
            ps.setInt(3,appUserId);
            return ps;
        }, keyHolder);
        return (rowsAffected >0 ? item : null);
    }

    @Override
    public boolean deleteItemFromShelf(int itemId) {
        final String sql = "DELETE FROM item_shelf WHERE item_id = ?";
        int isDeleted=jdbcTemplate.update(sql, itemId);
        return isDeleted >0;
    }

    @Override
    public List<Item> findAll() {
        final String sql = "SELECT item_id, title, author, published, publisher, topic, pages, `language`, ia_id " +
                "FROM item; ";
        List<Item> items= jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate));
        return(items==null||items.size()==0 ? null : items);
    }
}
