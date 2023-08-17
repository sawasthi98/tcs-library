package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;

import java.util.List;

public class ItemShelfJdbcTemplateRepository implements ItemShelfRepository {
    @Override
    public Item findByitemId(int itemId) {
        return null;
    }

    // for sorting purposes
    // will need an order by in the sql?
    // will likely need to do the calculation in this repo (last page read divided by page amount (from item pagecount))
    @Override
    public List<Item> findByIncomplete() {
        return null;
    }

    @Override
    public Item addItemToShelf(int itemId) {
        return null;
    }

    @Override
    public boolean deleteItemFromShelf(int itemId) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }
}
