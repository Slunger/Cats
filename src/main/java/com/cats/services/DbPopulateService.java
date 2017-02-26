package com.cats.services;

import com.cats.model.Cat;
import com.cats.model.User;
import com.cats.services.cat.CatService;
import com.cats.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by andrey on 08.02.17.
 */
@Component
public class DbPopulateService {

    private static final Logger LOG = LoggerFactory.getLogger(DbPopulateService.class);

    private static final String CAT_IS_SAVED = "cat {} is saved";
    private static final String USER_IS_SAVED = "user {} is saved";

    @Autowired
    private CatService catService;

    @Autowired
    private UserService userService;

    @PostConstruct
    private void populate() {

        if (!userService.getAll().isEmpty()) {
            return;
        }

        LOG.info("going to populate database with test data");

        User user1 = new User("password", "username");
        userService.save(user1);
        LOG.info(USER_IS_SAVED, user1);

        User user2 = new User("qwerty", "bruce");
        userService.save(user2);
        LOG.info(USER_IS_SAVED, user2);

        if (!catService.getAll().isEmpty()) {
            return;
        }

        int age = 1, weight = 1;
        Cat cat = new Cat(age++, "white", "Cyprus", "Kitty", weight++, user1.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "lavender", "Sphynx", "Byte", weight++, user2.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age, "chocolate", "European Shorthair", "Yoda", weight, user1.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "tortoiseshell", "Siamese", "Picasso", weight++, user2.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "black", "Exotic Shorthair", "Lancelot", weight++, user1.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age, "chocolate", "Asian", "eEwok", weight, user2.getId());
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);
    }
}
