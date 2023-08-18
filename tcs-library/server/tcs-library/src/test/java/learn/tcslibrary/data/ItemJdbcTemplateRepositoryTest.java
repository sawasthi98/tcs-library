package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemJdbcTemplateRepositoryTest {

    @Autowired
    private ItemJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindByItemId() {
        List<String> topics = new ArrayList<>();
        topics.add("Adventure");

        Item item = new Item();
        item.setItemId(2);
        item.setAuthor("Mark Twain");
       // item.setLanguage("English");
       // item.setPublisher("Magic Books");
        item.setTitle("Hucklebery Finn");
        item.setInternetArchiveIdentifier("221xyz");
       // item.setPublishedDate("December 5th, 1913");
        item.setTopic(topics);

        Item foundItem = repository.findByItemId(2);

        assertEquals(foundItem.getItemId(),2);
        assertEquals(foundItem.getTitle(),item.getTitle());
        assertEquals(foundItem.getAuthor(),item.getAuthor());
        assertEquals(foundItem.getInternetArchiveIdentifier(),item.getInternetArchiveIdentifier());
        assertEquals(foundItem.getTopic(),item.getTopic());
    }

    @Test
    void findByTopic() {
        List<String> topics = new ArrayList<>();
        topics.add("Adventure");

        Item item = new Item();
        item.setItemId(2);
        item.setAuthor("Mark Twain");
        //item.setLanguage("English");
      //  item.setPublisher("Magic Books");
        item.setTitle("Hucklebery Finn");
        item.setInternetArchiveIdentifier("221xyz");
       // item.setPublishedDate("December 5th, 1913");
        item.setTopic(topics);

        Item foundItem = repository.findByTopic("adventure");
        // will there be an issue with casing here?

        assertEquals(foundItem.getItemId(),2);
        assertEquals(foundItem.getTitle(),item.getTitle());
        assertEquals(foundItem.getAuthor(),item.getAuthor());
        assertEquals(foundItem.getInternetArchiveIdentifier(),item.getInternetArchiveIdentifier());
        assertEquals(foundItem.getTopic(),item.getTopic());
    }

    @Test
    void findByTitle() {
        List<String> topics = new ArrayList<>();
        topics.add("Adventure");

        Item item = new Item();
        item.setItemId(2);
        item.setAuthor("Mark Twain");
        //item.setLanguage("English");
      //  item.setPublisher("Magic Books");
        item.setTitle("Hucklebery Finn");
        item.setInternetArchiveIdentifier("221xyz");
     //   item.setPublishedDate("December 5th, 1913");
        item.setTopic(topics);

        Item foundItem = repository.findByTitle("Hucklebery Finn");
        // will there be an issue with casing here? cn says: its case sensitive unfortunately, we can work on that tho

        assertEquals(foundItem.getItemId(),2);
        assertTrue(foundItem.getTitle().contains(item.getTitle()));
        assertEquals(foundItem.getAuthor(),item.getAuthor());
        assertEquals(foundItem.getInternetArchiveIdentifier(),item.getInternetArchiveIdentifier());
        assertEquals(foundItem.getTopic(),item.getTopic());
    }

    @Test
    void shouldNotFindNonexistentItem () {
        Item nonexistentTitle = repository.findByTitle("test item");
        Item nonexistentItemId = repository.findByItemId(3);
        Item nonexistentTopic = repository.findByTopic("test topic");

        assertNull(nonexistentTitle);
        assertNull(nonexistentItemId);
        assertNull(nonexistentTopic);
        // is more necessary here?
    }
}