package learn.tcslibrary.data;


import learn.tcslibrary.App;
import learn.tcslibrary.domain.Result;
import learn.tcslibrary.models.AppUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    private AppUserJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindJohnSmith () {
        String username = "john@smith.com";
        AppUser user = repository.findByUsername(username);

        assertEquals(username, user.getUsername());
        assertEquals(1,user.getAppUserId());
    }

    @Test
    void shouldNotFindNonexistentKareem() {
        String username = "kareem@bballer.com";
        AppUser user = repository.findByUsername(username);

        assertNull(user);
    }

    @Test
   void shouldCreateNewUser () {
       // had some difficulty writing this test for the authorities portion where I instantiated a roles List
       List<String> roles = new ArrayList<>();
    roles.add("USER");

        AppUser testUser = new AppUser(3,"Test User","$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",true,roles);

        AppUser createdUser = repository.create(testUser);

        assertNotNull(createdUser);
        assertEquals(createdUser.getUsername(),testUser.getUsername());
   }

//    @Test
//    void shouldUpdatePassword() {
//        String username = "john@smith.com";
//        AppUser user = repository.findByUsername(username);
//
//        user.setPassword()
//    }



}