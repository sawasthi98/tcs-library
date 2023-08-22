package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemShelfRepository {

    public Item findByitemId(int itemId);
    // sorting purposes
//    public List<Item> findByIncomplete();
    public boolean updatePageNumber(int pageNumber, int itemId);
    public Item addItemToShelf(int itemId, int appUserId);
    public boolean deleteItemFromShelf(int itemId,int appUserId);

}
