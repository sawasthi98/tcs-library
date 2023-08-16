package learn.tcslibrary.data;


import learn.tcslibrary.App;
import learn.tcslibrary.models.AppUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Component
public interface AppUserRepository {

    // so we can use it to update password
    @Transactional
    public AppUser findByUsername(String username);

    @Transactional
    List<String> findRolesByUsername(String username);

    // create
    @Transactional
    public AppUser create(AppUser user);

    public List<AppUser>findAll();


}