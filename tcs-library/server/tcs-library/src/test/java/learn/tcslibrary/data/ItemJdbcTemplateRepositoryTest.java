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
        Item item = new Item();
        item.setItemId(1);

        item.setTitle("Pride and Prejudice");
        item.setInternetArchiveIdentifier("623abcdef");
        item.setSubject("literature");
        item.setDescription("a classic");

        Item foundItem = repository.findByItemId(1);

        assertEquals(foundItem.getItemId(),1);
        assertEquals(foundItem.getTitle(),item.getTitle());
        assertEquals(foundItem.getInternetArchiveIdentifier(),item.getInternetArchiveIdentifier());
    }

    @Test
    void findByTitle() {
        Item item = new Item();
        item.setItemId(1);

        item.setTitle("Pride and Prejudice");
        item.setInternetArchiveIdentifier("623abcdef");
        item.setSubject("literature");
        item.setDescription("a classic");

        Item foundItem = repository.findByTitle("Pride and Prejudice");
        // will there be an issue with casing here? cn says: its case sensitive unfortunately, we can work on that tho

        assertEquals(foundItem.getItemId(),1);
        assertTrue(foundItem.getTitle().contains(item.getTitle()));
        assertEquals(foundItem.getInternetArchiveIdentifier(),item.getInternetArchiveIdentifier());
    }

    @Test
    void shouldNotFindNonexistentItem () {
        Item nonexistentTitle = repository.findByTitle("test item");
        Item nonexistentItemId = repository.findByItemId(7);

        assertNull(nonexistentTitle);
        assertNull(nonexistentItemId);
        // is more necessary here?
    }
}