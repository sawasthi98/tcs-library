package learn.tcslibrary.domain;

import learn.tcslibrary.data.AppUserJdbcTemplateRepository;
import learn.tcslibrary.data.DataAccessException;
import learn.tcslibrary.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppUserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserJdbcTemplateRepository repository;

    @Test
    void shouldFindJohnSmith () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        String username = "john@smith.com";
        when(repository.findByUsername(username)).thenReturn(new AppUser(1,"john@smith.com","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles));

        AppUser foundUser = service.loadUserByUsername(username);

        assertEquals(foundUser.getUsername(),username);
    }

    @Test
    void shouldCreateKareem () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        AppUser kareem = new AppUser(1, "kareem@bballer.com","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);


        AppUser createdKareem = repository.create(kareem);

        when(repository.create(createdKareem)).thenReturn(kareem);

        Result result = service.create(kareem.getUsername(),kareem.getPassword());

        assertTrue(result.isSuccess());
        assertNotNull(result.getErrorMessages());

    }

    @Test
    void shouldNotCreateDuplicateKareem () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        AppUser kareem = new AppUser(1, "kareem@bballer.com","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        when(repository.findByUsername(kareem.getUsername())).thenReturn(kareem);

        Result result = service.create(kareem.getUsername(), kareem.getPassword());

        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessages());
        assertTrue(result.getErrorMessages().contains("Username already exists. Choose another one."));
    }

    @Test
    void shouldNotCreateWithBlankOrNull () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser kareem = new AppUser(1, " ","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);
        AppUser nullKareem = new AppUser(1, null,"$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        when(repository.create(kareem)).thenReturn(kareem);
        when(repository.create(kareem)).thenReturn(nullKareem);

        Result result = service.create(kareem.getUsername(),kareem.getPassword());
        Result result1 = service.create(nullKareem.getUsername(),nullKareem.getPassword());

        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessages());
        assertTrue(result.getErrorMessages().contains("Username is required."));

        assertFalse(result1.isSuccess());
        assertNotNull(result1.getErrorMessages());
        assertTrue(result1.getErrorMessages().contains("Username is required."));
    }

}