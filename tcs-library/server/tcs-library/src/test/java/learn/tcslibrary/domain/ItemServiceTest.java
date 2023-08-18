//package learn.tcslibrary.domain;
//
//import learn.tcslibrary.data.AppUserJdbcTemplateRepository;
//import learn.tcslibrary.data.ItemJdbcTemplateRepository;
//import learn.tcslibrary.models.Item;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//class ItemServiceTest {
//
//    @Autowired
//    ItemService service;
//
//    @MockBean
//    ItemJdbcTemplateRepository repository;
//
//
//    @Test
//    void shouldFindSherlockHolmesByTitle () {
//        List<String> topics = new ArrayList<>();
//        topics.add("Mystery");
//
//        String title = "Sherlock Holmes";
//
//        when(repository.findByTitle(title)).thenReturn(new Item(
//             "Sherlock Holmes Mysteries", "Sir Arthur Conan Doyle","1928","Mystery Books",topics,200,"English","asdflkj",1
//        ));
//
//        Item foundBook = service.findByTitle("Sherlock Holmes");
//
//        assertEquals(foundBook.getTitle(),title);
//    }
//
//    @Test
//    void shouldNotFindIfSearchIsNullOrBlank () {
//        Item foundBookNull = service.findByTitle(null);
//        assertNull(foundBookNull);
//
//        Item foundBookEmpty = service.findByTitle("");
//        assertNull(foundBookEmpty);
//
//        Item foundBookWhitespace = service.findByTitle("    ");
//        assertNull(foundBookWhitespace);
//    }
//
//    @Test
//    void shouldFindSherlockHolmesByAuthor () {
//        List<String> topics = new ArrayList<>();
//        topics.add("Mystery");
//
//        String author = "Sir Arthur Conan Doyle";
//
//        when(repository.findByAuthor(author)).thenReturn(new Item(
//                "Sherlock Holmes Mysteries", author, "1928", "Mystery Books", topics, 200, "English", "asdflkj", 1
//        ));
//
//        Item foundBook = service.findByAuthor(author);
//
//        assertEquals(foundBook.getAuthor(), author);
//    }
//
//    @Test
//    void shouldFindSherlockHolmesByItemId() {
//        int itemId = 1;
//
//        when(repository.findById(itemId)).thenReturn(new Item(
//                "Sherlock Holmes Mysteries", "Sir Arthur Conan Doyle", "1928", "Mystery Books", new ArrayList<>(), 200, "English", "asdflkj", itemId
//        ));
//
//        Item foundBook = service.findByItemId(itemId);
//
//        assertEquals(foundBook.getItemId(), itemId);
//    }
//
//}