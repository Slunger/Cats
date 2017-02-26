package com.cats.services.user;

import com.cats.model.User;
import com.cats.services.ServicesTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

/**
 * Created by andrey on 26.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesTestConfig.class})
@ActiveProfiles("dev")
public class UserServiceTest {

    private static final String PASSWORD = "password";
    public static final String USER_NOT_EXIST = "user_not_exist";
    public static final String TOKEN = "token";

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() {
        final User user = new User(PASSWORD, "name");
        userService.save(user);
        assertNotNull(userService.get(user.getId()));
    }

    @Test
    public void testGetUserByUsername() {
        final User user = new User(PASSWORD, "Gru");
        userService.save(user);

        assertEquals(userService.loadUser(user.getUsername()).getUsername(), user.getUsername());
        assertNull(userService.loadUser(USER_NOT_EXIST));
    }

    @Test
    public void testUpdateCat() {
        final User user = new User(PASSWORD, "Brie");
        userService.save(user);
        userService.updateTokenByUsername(user.getUsername(), TOKEN);
        assertEquals(userService.get(user.getId()).getToken(), TOKEN);

    }

    @Test
    public void testGetAll() {
        assertTrue(userService.getAll().size() > 0);
    }
}
