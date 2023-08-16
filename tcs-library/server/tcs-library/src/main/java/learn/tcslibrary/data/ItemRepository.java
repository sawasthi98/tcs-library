package learn.tcslibrary.data;

import learn.tcslibrary.models.Item;

public interface ItemRepository {

    public Item findByItemId(int itemId);
    public Item findByTopic(String topic);
    public Item findByTitle(String title);

}
