package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.ItemMapper;
import learn.tcslibrary.models.Item;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import learn.tcslibrary.models.ItemShelf;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository

public class ItemJdbcTemplateRepository implements ItemRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item findByItemId(int itemId) {
        final String sql = "select item_id, identifier, filename " +
                "from item where item_id = ? ;";

        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),itemId);
        return(itemList == null||itemList.size() == 0 ? null : itemList.get(0));
    }

//
//    @Override
//    public Item findByTopic(String topic) {
//        final String sql="select item_id, title, author,  topic, " +
//                " ia_id from item where topic=?;";
//        //List<String>topics=findTopicsByTopic(topic);
//        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),topic);
//        return(itemList == null||itemList.size()==0 ? null : itemList.get(0));
//    }

    @Override
    public Item findByTitle(String title) {
        final String sql = "select item_id, identifier, filename " +
                " from item where filename = ?;";
        //List<String>topics=findTopicsByTitle(title);
        List<Item>items=jdbcTemplate.query(sql,new ItemMapper(jdbcTemplate),title);
        return(items == null||items.size()==0 ? null : items.get(0));
    }

    @Override
    public Item findByIdentifier(String identifier){
        final String sql="select item_id, identifier, filename " +
                "from item where identifier = ?;";
        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate), identifier);
        return(itemList == null||itemList.size()==0 ? null : itemList.get(0));
    }

    @Override
    public List<Item> findItemsByItemShelf(ItemShelf itemShelf){
        final String sql = "  select i.item_id, i.identifier, i.filename from item i " +
                "inner join item_shelf shelf on i.item_id = shelf.item_id " +
                "where shelf.item_id = ?;";

        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),itemShelf.getItemShelfId());
        return(itemList == null || itemList.size() == 0 ? null : itemList);
    }

    @Override
    public Item addItemMetadata(String identifier, String filename) {
        final String sql = "insert into item (identifier, filename) values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, identifier);
            ps.setString(2, filename);
            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        int itemId = keyHolder.getKey().intValue();
        Item item = new Item();
        item.setItemId(itemId);
        item.setIdentifier(identifier);
        item.setFileName(filename);

        return item;
    }
}
