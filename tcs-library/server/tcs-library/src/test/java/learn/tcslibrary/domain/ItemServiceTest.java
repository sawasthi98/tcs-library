package learn.tcslibrary.domain;

import learn.tcslibrary.data.ItemJdbcTemplateRepository;
import learn.tcslibrary.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService service;

    @MockBean
    ItemJdbcTemplateRepository repository;

    @Test
    void shouldFindSherlockHolmesByTitle () {

        String title = "Sherlock Holmes";
        Item sherlock = new Item("Sherlock Holmes", "asdflkj", "cool description", "mystery", "book.pdf", "picture of sherlock holmes.com");
        sherlock.setItemId(1);

        when(repository.findByTitle(title)).thenReturn(sherlock);

        Item foundBook = service.findByTitle(title);

        assertEquals(foundBook.getTitle(),title);
    }

    @Test
    void shouldNotFindIfSearchIsNullOrBlank () {
        Item foundBookNull = service.findByTitle(null);
        assertNull(foundBookNull);

        Item foundBookEmpty = service.findByTitle("");
        assertNull(foundBookEmpty);

        Item foundBookWhitespace = service.findByTitle("    ");
        assertNull(foundBookWhitespace);
    }
    @Test
    void shouldFindSherlockHolmesByItemId() {
        Item sherlock = new Item("Sherlock Holmes Mysteries", "asdflkj", "cool description", "mystery", "book.pdf", "picture of sherlock holmes.com");
        sherlock.setItemId(2);

        when(repository.findByItemId(2)).thenReturn(sherlock);

        Item foundBook = service.findItemByItemId(sherlock.getItemId());

        assertEquals(foundBook.getItemId(), sherlock.getItemId());
    }

}