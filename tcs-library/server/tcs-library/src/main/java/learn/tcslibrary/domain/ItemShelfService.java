package learn.tcslibrary.domain;

import learn.tcslibrary.data.AppUserJdbcTemplateRepository;
import learn.tcslibrary.data.ItemJdbcTemplateRepository;
import learn.tcslibrary.data.ItemShelfJdbcTemplateRepository;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.AppUser;
import learn.tcslibrary.models.ItemShelf;
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

    public Result<Item> findOrAddToShelf(String internetArchiveId, int appUserId){
        Item item = itemRepository.findByInternetArchiveId(internetArchiveId);
        AppUser appUser = appUserRepository.findUserByAppUserId(appUserId);

        Result result = validate(item, appUser);

        if(!result.isSuccess()) {
            return result;
        }

        List<Item> itemsList = itemShelfRepository.findByAppUserId(appUserId);
        if (itemsList != null) {
            for (Item i : itemsList) {

                if(i.getItemId() == item.getItemId() &&
                        i.getInternetArchiveIdentifier().equals(item.getInternetArchiveIdentifier())) {

                    result.setPayload(item);
                    return result; // this one already exists
                }

            } //end of fourghleiuppe
        }

        Item addedItem = itemShelfRepository.addItemToShelf(item.getItemId(), appUser.getAppUserId());
        result.setPayload(addedItem);

        return result;
    }

    public boolean updatePageNumber(int appUserId, int itemId, int pageNumber) {

        List<Item> bookshelf = itemShelfRepository.findByAppUserId(appUserId);
        for (Item item : bookshelf) {
            if (item.getItemId() == itemId) {
                boolean result = itemShelfRepository.updatePageNumber(pageNumber,itemId);
                return result;

            }
        }
        return false;
    }


    public boolean deleteItemFromShelf(Item item, AppUser appUser){
        return itemShelfRepository.deleteItemFromShelf(item.getItemId(),appUser.getAppUserId());
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
