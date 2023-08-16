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

    // validation
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
    void shouldCreateKaren () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser karen = new AppUser(1, "karen@getmethemanager.com","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        AppUser createdKaren = repository.create(karen);

        when(repository.create(createdKaren)).thenReturn(karen);

        Result result = service.create(karen.getUsername(),karen.getPassword());

        assertTrue(result.isSuccess());
        assertNotNull(result.getErrorMessages());

    }

    @Test
    void shouldNotCreateDuplicateKaren () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser karen = new AppUser(1, "karen@getmethemanager.com","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        when(repository.create(karen)).thenReturn(karen);

        Result result = service.create(karen.getUsername(), karen.getPassword());

        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessages());
        assertTrue(result.getErrorMessages().contains("Username already exists. Choose another one."));
    }

    @Test
    void shouldNotCreateWithBlankOrNull () {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser karen = new AppUser(1, " ","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);
        AppUser nullKaren = new AppUser(1, null,"$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        when(repository.create(karen)).thenReturn(karen);
        when(repository.create(karen)).thenReturn(nullKaren);

        Result result = service.create(karen.getUsername(),karen.getPassword());
        Result result1 = service.create(nullKaren.getUsername(),nullKaren.getPassword());

        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessages());
        assertTrue(result.getErrorMessages().contains("Username cannot be blank."));

        assertFalse(result1.isSuccess());
        assertNotNull(result1.getPayload());
        assertTrue(result1.getErrorMessages().contains("Username cannot be null."));
    }

}