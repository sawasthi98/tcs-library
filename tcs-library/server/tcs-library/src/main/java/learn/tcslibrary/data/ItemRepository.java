package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository {

    public Item findByItemId(int itemId);
    public Item findByTopic(String topic);
    public Item findByTitle(String title);
    public List<Item> findItemsByItemShelf(ItemShelf itemShelf);

}
