package learn.tcslibrary.domain;


import learn.tcslibrary.data.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    public ItemRepository repository;

    //Is it really necessary to do a validate if each item may vary in what it does/doesnt have? -CN


}
