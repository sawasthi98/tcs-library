package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository {
    public Item findByItemId(int itemId);
    public Item findByTitle(String title);
    public Item findByInternetArchiveId(String iaId);
    public List<Item> findItemsByItemShelf(ItemShelf itemShelf);
    public Item addItemMetadata (String internetArchiveId, String filename);

}
