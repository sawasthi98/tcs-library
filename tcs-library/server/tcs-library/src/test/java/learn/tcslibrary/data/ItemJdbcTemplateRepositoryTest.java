package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        item.setLanguage("English");
        item.setPublisher("Magic Books");
        item.setTitle("Huckleberry Finn");
        item.setInternetArchiveIdentifier("221xyz");
        item.setPublishedDate("December 5th, 1913");
        item.setTopic(topics);

        Item foundItem = repository.findByItemId(1);

        assertEquals(foundItem.getItemId(),2);
        assertEquals(foundItem.getTitle(),item.getTitle());


    }

    @Test
    void findByTopic() {
    }

    @Test
    void findByTitle() {
    }
}