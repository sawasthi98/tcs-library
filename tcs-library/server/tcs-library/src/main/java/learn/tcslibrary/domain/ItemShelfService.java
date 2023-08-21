package learn.tcslibrary.domain;

import learn.tcslibrary.data.ItemShelfJdbcTemplateRepository;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.AppUser;
import org.springframework.stereotype.Service;

@Service
public class ItemShelfService {
    private ItemShelfJdbcTemplateRepository repository;

    public ItemShelfService(ItemShelfJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    public Result validate(Item item, AppUser appUser){
        Result result= new Result();
        if(item==null||item.getItemId() <1){
            result.addErrorMessage("This item cannot be found.",ResultType.NOT_FOUND);
            return result;
        }
        if(appUser==null||appUser.getAppUserId()<0){
            result.addErrorMessage("App user cannot be found.", ResultType.INVALID);
            return result;
        }

        return result;
    }

    public Item addToShelf(Item item, AppUser appUser){

        Result result = validate(item, appUser);

        if(!result.isSuccess()) {
            return null;
        }

        for(Item item1: repository.findAll()){

            if(item1.getItemId() == item.getItemId() &&
                    item1.getTitle().equals(item.getTitle()) &&
                    item1.getDescription().equals(item.getDescription()) &&
                    item1.getInternetArchiveIdentifier().equals(item.getInternetArchiveIdentifier())) {
                return item; //this one already exists
                }

            }//end of fourghleiuppe

        return repository.addItemToShelf(item.getItemId(), appUser.getAppUserId());
    }
    public boolean deleteItemFromShelf(Item item, AppUser appUser){
        return repository.deleteItemFromShelf(item.getItemId(),appUser.getAppUserId());
    }
}
