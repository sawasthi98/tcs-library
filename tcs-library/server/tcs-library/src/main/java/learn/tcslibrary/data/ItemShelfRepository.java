package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemShelfRepository {

    public List<ItemShelf> findByAppUserId(int appUserId);
    public boolean updatePageNumber(int pageNumber, int itemId, int appUserId);
    public ItemShelf addItemToShelf(int itemId, int appUserId);
    public boolean deleteItemFromShelf(int itemId,int appUserId);

}
