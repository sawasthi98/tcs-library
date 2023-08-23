package learn.tcslibrary.domain;

import learn.tcslibrary.data.AppUserJdbcTemplateRepository;
import learn.tcslibrary.data.ItemJdbcTemplateRepository;
import learn.tcslibrary.data.ItemShelfJdbcTemplateRepository;
import learn.tcslibrary.data.ItemShelfRepository;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.AppUser;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemShelfService {
    private ItemShelfJdbcTemplateRepository itemShelfRepository;
    private ItemJdbcTemplateRepository itemRepository;
    private AppUserJdbcTemplateRepository appUserRepository;

    public ItemShelfService(ItemShelfJdbcTemplateRepository itemShelfRepository, ItemJdbcTemplateRepository itemRepository, AppUserJdbcTemplateRepository appUserRepository) {
        this.itemShelfRepository = itemShelfRepository;
        this.itemRepository = itemRepository;
        this.appUserRepository = appUserRepository;
    }

    public Result<ItemShelf> findOrAddToShelf(String internetArchiveId, int appUserId){
        Item item = itemRepository.findByInternetArchiveId(internetArchiveId);
        AppUser appUser = appUserRepository.findUserByAppUserId(appUserId);

        Result result = validate(item, appUser);

        if(!result.isSuccess()) {
            return result;
        }
//        System.out.println("App user ID: "+appUserId);
//        System.out.println("Item ID: "+item.getItemId());
        try {
            ItemShelf itemShelf = itemShelfRepository.findByAppUserIdAndItemId(appUserId, item.getItemId());
        }
        catch (EmptyResultDataAccessException a){//app user doesnt have this in their shelf
            ItemShelf itemShelf=itemShelfRepository.addItemToShelf(item.getItemId(),appUserId);

            if(itemShelf == null) {
                ItemShelf addedItem = itemShelfRepository.addItemToShelf(item.getItemId(), appUser.getAppUserId());
                result.setPayload(addedItem);
            }
        }

        return result;
    }

    public List<ItemShelf> findByAppUserId(int appUserId) {
        return itemShelfRepository.findByAppUserId(appUserId);
    }

    public Result updatePageNumber(int appUserId, int itemId, int pageNumber) {
        Result result = new Result();
        //new repo method  where appuser id = ? and item id = ?

        ItemShelf bookshelf = itemShelfRepository.findByAppUserIdAndItemId(appUserId, itemId);

        boolean updated = itemShelfRepository.updatePageNumber(pageNumber,itemId, appUserId);
        if (updated) {
            result.setPayload(bookshelf);
        }

        return result;
    }


    public boolean deleteItemFromShelf(Item item, AppUser appUser){
        return itemShelfRepository.deleteItemFromShelf(item.getItemId(),appUser.getAppUserId());
    }

    public ItemShelf findByAppUserIdAndItemId(AppUser appUser, int itemId) {
        //new user?
        return itemShelfRepository.findByAppUserIdAndItemId(appUser.getAppUserId(), itemId);
    }


    public Result validate(Item item, AppUser appUser){
        Result result = new Result();

        if(item == null || item.getItemId() < 1){
            result.addErrorMessage("This item cannot be found.",ResultType.NOT_FOUND);
            return result;
        }
        if(appUser == null || appUser.getAppUserId() < 0){
            result.addErrorMessage("App user cannot be found.", ResultType.INVALID);
            return result;
        }

        return result;
    }
}
