package learn.tcslibrary.data;


import learn.tcslibrary.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {

    // so we can use it to update password
    @Transactional
    public int findByUsername(String username);

    // create
    @Transactional
    public AppUser create(AppUser user);


}