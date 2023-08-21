package learn.tcslibrary.domain;


import learn.tcslibrary.data.ItemRepository;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item findItemByItemId(int itemId){
        Item item = repository.findByItemId(itemId);
        Result result = validate(item);
        return (result.isSuccess() ? item : null);
    }

    public Item findByTitle(String title){
        Item item = repository.findByTitle(title);
        Result result = validate(item);
        return (result.isSuccess() ? item : null);
    }

    public Item findOrCreate(String internetArchiveId, String filename) {
        // check if exists
        Item item = repository.findByInternetArchiveId(internetArchiveId);

        if (item != null) {
            // send to frontend
            return item;
        }
            // save to database
           return repository.addItemMetadata(internetArchiveId, filename);
    }

    public Result validate(Item item){
        Result result= new Result();
        if(item==null||item.getItemId()<1){
            result.addErrorMessage("Item is null",ResultType.INVALID);
            return result;
        }
        if(item.getInternetArchiveIdentifier()==null){
            result.addErrorMessage("Unable to find IA Identifier",ResultType.INVALID);
            return result;
        }
        return result;
    }

    public List<Item> findItemsByItemShelf(ItemShelf itemShelf){
        return repository.findItemsByItemShelf(itemShelf);
    }
}
