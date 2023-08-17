package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;

import java.util.List;

public interface ItemShelfRepository {

    public Item findByitemId(int itemId);
    // sorting purposes
    public List<Item> findByIncomplete();
    public Item addItemToShelf(int itemId);
    public boolean deleteItemFromShelf(int itemId);
    public List<Item> findAll();


}
