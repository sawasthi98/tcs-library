package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemShelfJdbcTemplateRepositoryTest {
    @Autowired
    private ItemShelfJdbcTemplateRepository repository;

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
    void findByitemId() {
        Item item = repository.findByitemId(1);
        assertNotNull(item);
        assertEquals(1, item.getItemId());
    }

//    @Test
//    void findByIncomplete() {
//        Item item= new Item();
//        item.setAuthor("test");
//        item.setItemId(15);
//        repository.addItemToShelf(item.getItemId(),1);
//        List<Item> incompleteItems = repository.findByIncomplete();
//        assertNotNull(incompleteItems);
//        assertFalse(incompleteItems.isEmpty());
//
//        boolean isDeleted = repository.deleteItemFromShelf(item.getItemId(), 1);
//        assertTrue(isDeleted);
// this test doesnt work right now. -cn
//    }

    @Test
    void addItemToShelf() {
        Item addedItemShelf = repository.addItemToShelf(1, 1);
        assertNotNull(addedItemShelf);
        assertEquals(1, addedItemShelf.getItemId());

    }

    @Test
    void deleteItemFromShelf() {
        boolean isDeleted = repository.deleteItemFromShelf(1,1);
        assertTrue(isDeleted);
    }

    @Test
    void findAll() {
        List<Item> allItemShelf = repository.findAll();
        assertNotNull(allItemShelf);
        assertFalse(allItemShelf.isEmpty());
        assertEquals(2,allItemShelf.size());
    }
    @Test
    void findByAppUserId() {
        List<Item> items = repository.findByAppUserId(1);
        assertNotNull(items);
        assertFalse(items.isEmpty());
    }
}
