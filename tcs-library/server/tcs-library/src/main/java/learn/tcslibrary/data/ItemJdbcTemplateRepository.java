package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.ItemMapper;
import learn.tcslibrary.models.Item;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class ItemJdbcTemplateRepository implements ItemRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item findByItemId(int itemId) {
        final String sql = "select item_id, title, author, published, publisher, " +
                "topic, pages, `language`, ia_id " +
                "from item where item_id= ? ;";
        //List<String>topics=findTopicsByItemId(itemId);
        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),itemId);
        return(itemList == null||itemList.size()==0 ? null : itemList.get(0));
    }
    public List<String> findTopicsByItemId(int itemId){
        final String sql = "select topic from item where item_id= ? ;";
        return jdbcTemplate.queryForList(sql, String.class, itemId);
    }

    @Override
    public Item findByTopic(String topic) {
        final String sql="select item_id, title, author, published, publisher, topic, " +
                "pages, `language`, ia_id from item where topic=?;";
        //List<String>topics=findTopicsByTopic(topic);
        List<Item> itemList = jdbcTemplate.query(sql, new ItemMapper(jdbcTemplate),topic);
        return(itemList == null||itemList.size()==0 ? null : itemList.get(0));
    }

    public List<String> findTopicsByTopic(String topic) {
        final String sql = "select topic from item where topic = ?;";
        return jdbcTemplate.queryForList(sql, String.class, topic);
    }

    @Override
    public Item findByTitle(String title) {
        final String sql = "select item_id, title, author, published, publisher, topic, " +
                "pages, `language`, ia_id from item where title= ?;";
        //List<String>topics=findTopicsByTitle(title);
        List<Item>items=jdbcTemplate.query(sql,new ItemMapper(jdbcTemplate),title);
        return(items == null||items.size()==0 ? null : items.get(0));
    }
    public List<String>findTopicsByTitle(String title){
        final String sql = "select topic from item where title = ?;";
        return jdbcTemplate.queryForList(sql, String.class, title);
    }
}
